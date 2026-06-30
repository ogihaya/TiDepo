# AGENTS.md — TiDepo 開発規約（骨子）

本書は TiDepo の開発規約の骨子である。Java / TypeScript の詳細なコーディング規約は、各 scaffold 手順（Phase 0 手順 0-2 / 0-3）で追記する。

## ディレクトリ構成

```text
backend/    # Java / Spring Boot。オニオンアーキテクチャ（domain / application / infrastructure / presentation / config）
frontend/   # TypeScript / React。FSD（app / pages / widgets / features / entities / shared）
infra/      # AWS CDK（TypeScript）
docs/       # ドキュメント
```

## ドキュメント配置

- 公開（git 管理）: `docs/requirements.md`（要件定義書。ソース・オブ・トゥルース）。
- 管理外（`docs/private/`、gitignore 済み）: ロードマップ、実装方針・手順書（`plan/`）、設計書（`design/`）など作業ドキュメント。

## アーキテクチャ原則

- バックエンドはオニオンの依存方向（内向き）を守る。ドメイン層は他層・外部サービスに依存しない。
- 猶予時間計算・繰り返し予定の発生判定・日付処理はドメインの純粋ロジックとして実装し、単体テストで保護する。
- Google 連携（同期）は腐敗防止層（ACL）/独立モジュールとして隔離する。
- フロントエンドは FSD の依存方向（上→下の一方向）と public API 境界を守る。
- 認証はブラウザ↔サーバ間を BFF パターン（トークンはサーバ側保持、httpOnly セッションCookie）とする。

## ブランチ規約

ベースは `main`。手順（PR）単位でブランチを切る。

- 機能: `feat/issue-<番号>-<短い英語>`
- 修正: `fix/issue-<番号>-<短い英語>`
- リファクタ: `refact/issue-<番号>-<短い英語>`
- ドキュメント: `docs/issue-<番号>-<短い英語>`
- 雑務: `chore/issue-<番号>-<短い英語>`

`main` へ直接 push しない。

## コミット規約

- 日本語で書く。1 コミット 1 論点。
- 1 行目に変更内容を簡潔に、必要なら本文に「なぜ」を書く。
- AI 生成フッターや Co-Authored-By は付けない。
- secret / credential（OAuth クライアントシークレット、トークン、AWS 鍵等）を含めない。

## PR 運用

- 1 PR = 1 手順を基本とする。
- 本文に `Closes #<Issue番号>` で関連 Issue を紐付ける。
- CI green・レビュー解決・受入条件達成を確認してからマージする。

## 品質ゲート（各フェーズで具体化）

- バックエンド（Gradle）: `./gradlew spotlessApply` → `./gradlew check` → ArchUnit によるオニオン依存検証 → `./gradlew test`。
- フロントエンド: `npm run lint` → `npm run typecheck` → `npm test`（Vitest）→ `npm run build`。
- CI（GitHub Actions）で上記を自動実行する（Phase 0 手順 0-6 で構築）。

## バックエンド規約（Phase 0-2 で確定）

- Java 21 / Spring Boot 4.x / Gradle（Kotlin DSL）。ベースパッケージ `com.tidepo`。
- 単一モジュール構成。オニオンの層はパッケージで表現し、依存方向は ArchUnit（`OnionArchitectureTest`）で検証する。
- 整形は Spotless + palantir-java-format（4スペース。`.editorconfig` の Java=4 と整合）。Checkstyle はフォーマット系を持たせず最小ルールのみ。
- ドメイン層は他層・外部フレームワークに依存しない（純粋ロジック）。
- 詳細は [`backend/README.md`](./backend/README.md) を参照。

## フロントエンド規約（Phase 0-3 で確定）

- TypeScript / React 19 / Vite 8。パッケージ管理は npm。パスエイリアス `@/*` → `src/*`。
- FSD（app/pages/widgets/features/entities/shared）。各レイヤーは `index.ts`（public API）経由で公開。
- 依存方向（app→pages→widgets→features→entities→shared の一方通行）は ESLint の eslint-plugin-boundaries で強制（`@` エイリアス解決のため eslint-import-resolver-typescript を併用）。
- 整形は Prettier（eslint-config-prettier で ESLint と非競合）。Lint は ESLint flat config。
- テストは Vitest ＋ React Testing Library（jsdom）。
- 詳細は [`frontend/README.md`](./frontend/README.md) を参照。

## 補助ツール

`.claude/` に開発フロー用の skill / agent を用意している（設計→分解→計画→実装→commit→PR→レビュー→merge→Issue完了）。
