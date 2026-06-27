---
name: tidepo-ci
description: TiDepo repo専用。PRのCIステータスを確認し、失敗があればローカルで再現・修正してpushまで行う。
allowed-tools: Bash(gh *), Bash(./gradlew *), Bash(npm *), Bash(npx *), Bash(docker *), Bash(git *), Read, Edit, Write, Glob, Grep
---

現在のブランチのPRに対するCIの実行状況を確認し、失敗があればローカルで再現・修正してpushまで行う。リポジトリは `ogihaya/TiDepo`。

## 手順

### 1. CI ステータス確認

- `gh pr checks` で現在のPRのCI結果を取得する。
- 全ジョブの pass / fail / pending を一覧表示する。
- 全て pass なら「CI全通過」と報告して終了する。

### 2. 失敗ジョブの詳細取得

- 失敗したジョブのログを `gh run view <run-id> --log-failed` で取得する。
- エラー内容を分析し、原因を特定する。

### 3. ローカルで再現・修正

失敗したジョブに応じて、対応するローカルコマンドで再現・修正する（プロジェクトのCI定義に合わせる）。

| 想定CIジョブ | ローカル再現コマンド |
|--------------|---------------------|
| Backend - 整形/静的解析 | `./gradlew spotlessCheck check` |
| Backend - オニオン依存検証(ArchUnit) | `./gradlew test`（または専用タスク） |
| Backend - テスト | `./gradlew test` |
| Backend - ビルド/Docker | `./gradlew build` / `docker compose build backend` |
| Frontend - Lint/型 | `npm run lint && npm run typecheck` |
| Frontend - テスト | `npm test` |
| Frontend - ビルド | `npm run build` |

- エラー箇所を修正する。
- 修正後、同じコマンドでローカル通過を確認する。

### 4. コミット & プッシュ

- `tidepo-commit` でコミットメッセージを生成・コミットする。
- `git push` してCIの再実行を待つ。

### 5. CI 再確認

- push 後、`gh pr checks --watch` でCIの再実行結果を監視する。
- 全て pass したら「CI全通過」と報告する。

## 注意

- Backend CIは `backend/**`、Frontend CIは `frontend/**` のパス変更時のみトリガーされる構成を想定（実際のworkflow定義に従う）。
- 具体的なGradleタスク名・npm scriptは scaffold 後の設定に合わせる。
- コミットメッセージに AI 生成フッターは付けない。
