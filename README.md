# TiDepo

予定とタスクを同じカレンダー上で管理し、締切までに **「本当に残っている作業可能時間（猶予時間）」** を可視化する個人向けタスク管理アプリ。

日々の予定で使えない時間・タスクの見積もり時間・予定が未確定な期間の見込みを踏まえて、タスクごとの猶予時間を表示する。これにより「どのタスクに早く着手すべきか」「今の予定量で締切に間に合うか」を判断できる。

## 主要機能（MVP）

- 予定・タスク・一時的なタスクの管理
- 猶予時間の算出・可視化（完了タスク除外・未確定期間の見込み対応）
- タスクの入れ子（親子分割。最上位タスクがまとまりを兼ねる）
- カレンダー表示（月/週/日）
- 認証（Google ＋ メール/パスワード、OAuth2.0/PKCE、ロール）
- Google Calendar / Google Tasks との双方向同期
- 変更履歴の監査、レスポンシブWeb

詳細は [`docs/requirements.md`](./docs/requirements.md) を参照。

## 技術スタック

- バックエンド: Java / Spring Boot（オニオンアーキテクチャ）
- フロントエンド: TypeScript / React（FSD）
- データストア: PostgreSQL
- インフラ: Docker、AWS（ECS/Fargate + RDS、IaC は AWS CDK/TypeScript）、CI/CD
- 外部連携: Google Calendar API / Google Tasks API

## ディレクトリ構成

```text
backend/    # Java / Spring Boot（オニオン）
frontend/   # TypeScript / React（FSD）
infra/      # AWS CDK（TypeScript）
docs/       # ドキュメント（requirements.md ほか）
```

## 開発の進め方

- 要件のソース・オブ・トゥルースは [`docs/requirements.md`](./docs/requirements.md)。
- 開発規約は [`AGENTS.md`](./AGENTS.md) を参照。
- ロードマップ・設計書・実装計画など作業ドキュメントは `docs/private/`（リポジトリ管理外）で管理する。

## ステータス

開発初期（Phase 0: 環境・基盤準備）。
