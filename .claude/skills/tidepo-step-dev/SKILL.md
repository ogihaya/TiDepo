---
name: tidepo-step-dev
description: TiDepo repo専用。詳細実装計画書に基づき、指定手順を実装し、品質ゲート、subagentレビュー修正ループまで進め、差分を未コミットのまま確認可能な状態にする。
---

## 入力

- 詳細実装計画書のパス（`docs/plan/` 配下）
- 対象手順番号
- レビュー修正ループ上限。指定がなければ3回
- docs/config軽量モードを明示する追加指示がある場合はその旨

## 基本方針

- このskillはTiDepo向けの実装workflowとして使う。
- 実装前に現在branch、既存差分、詳細実装計画書、関連設計書、関連Issueを確認する。
- 既存のユーザー変更を巻き戻さない。unrelated changeを勝手に含めない。
- secret、credential（Google OAuthシークレット、AWS鍵等）、巨大ファイルを混ぜない。
- 未実行テストは隠さず報告する。destructive git操作は行わない。
- このskillではcommitを作らない。`git add`、`git commit`、`tidepo-commit` の呼び出しは禁止する。
- commitが必要な場合は、最終報告で推奨粒度、対象ファイル、commit message案を提案するだけに留める。
- GitHub Issue完了処理はこのskillでは行わない。

## 通常モード

backend / frontend コード変更、DB変更、API変更、Google Calendar連携変更を含む場合は通常モードで進める。

1. `git status` と必要なdiffを確認する。
2. 詳細実装計画書、関連設計書、実装方針・手順書、関連Issueを読む。
3. 対象手順の目的、完了条件、対象外、既知制約を整理する。
4. 変更予定ファイルと既存差分を確認し、unrelated changeを混ぜない。
5. 既存実装パターンを確認してから実装する（Backend オニオン、Frontend FSD の依存方向を守る）。
6. 関連する最小テストを実行する。
7. 品質ゲートを実行する（下記基準）。
8. 品質ゲート後に `git status` と差分概要を確認し、差分を未コミットのまま残す。
9. 必要に応じて `tidepo-implementation-reviewer` サブエージェント（Agent tool / `subagent_type: tidepo-implementation-reviewer`）にレビューを依頼する。

## 品質ゲート基準（プロジェクトのbuild設定に従う）

- Backend（Gradle）: `./gradlew spotlessApply`（整形）→ `./gradlew check`（静的解析）→ ArchUnitによるオニオン依存検証（`./gradlew test` または専用タスク）→ `./gradlew test`（テスト）。
- Frontend: `npm run lint` → `npm run typecheck`（`tsc --noEmit`）→ `npm test`（Vitest）→ `npm run build`。
- Docker前提でローカル実行できない場合は `docker compose exec` 経由で実行してよい。
- 具体的なタスク名・script名は scaffold 後の設定に合わせる。設定未整備で実行できないゲートは、その旨と理由を明記する。

## docs/config軽量モード

docs、設定整理、skills、subagents、rulesなど、アプリケーション挙動を変えない作業では軽量モードを使える。

- レビュー修正ループは、ユーザーの明示指示がある場合だけ実行する。
- commitはこのskillでは行わない。
- 差分確認、unrelated change確認、secret/巨大ファイル確認、未実行テスト報告は省略しない。
- backend/frontendの品質ゲートを実行しない場合は、理由を明記する。
- 途中でbackend/frontendコード変更、DB変更、API変更が入った場合は通常モードに戻す。

## TDD方針

- 純粋ロジック（猶予時間計算・繰り返し予定の発生判定・日付処理）、domain rule、複雑な application 処理では RED/GREEN/REFACTOR を強く推奨する。
- REDでは失敗するテストを書き、失敗理由が想定どおりか確認する。
- GREENではテストを通す最小実装に留める。
- REFACTORでは機能追加せず、構造・命名・重複を整える。
- docs/configのみの作業ではTDD不要。
- テスト失敗、lint失敗、オニオン依存検証失敗を無視して次へ進まない。

## review_roundルール

- レビュー修正ループは最大3回。
- ループ回数は親エージェントが `review_round=1..3` として明示的に管理する。
- subagentへ渡す文脈が不足している状態でレビューを依頼しない。
- レビュー指摘は採否判断してから修正する。
- `review_round == 3` のレビュー結果を受け取ったら、High/Criticalが残っていても追加実装せず停止する。
- 実装範囲外の改善案や設計変更は、原則として残課題へ回す。

## subagent方針

- レビューは `.claude/agents/tidepo-implementation-reviewer.md`（Agent tool / `subagent_type: tidepo-implementation-reviewer`）を使う。
- subagent依頼には、詳細実装計画書、関連設計書、対象手順、目的、完了条件、対象外、既知制約、対象差分、実行済みテスト、未実行テスト、既知の未解決事項、現在の `review_round` を渡す。
- 実装前のコード調査が必要なら `tidepo-code-explorer`、外部仕様確認が必要なら `tidepo-docs-researcher` を使う。

## 出力

- 実装結果 / 変更ファイル
- 実行済みテスト / 未実行テストと理由
- 未コミット差分の状態
- commitする場合の推奨粒度、対象ファイル、commit message案
- review_round結果 / 未解決指摘と採否判断
- 次手順への申し送り
