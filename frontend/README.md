# frontend

TiDepo のフロントエンド。

## スタック

- 言語/FW: TypeScript / React 19
- ビルド/開発: Vite 8
- アーキテクチャ: FSD（app / pages / widgets / features / entities / shared）
- Lint: ESLint（flat config）＋ eslint-plugin-boundaries（FSD 依存方向の強制）
- 整形: Prettier
- テスト: Vitest ＋ React Testing Library（jsdom）
- パスエイリアス: `@/*` → `src/*`

## ディレクトリ（FSD レイヤー）

```text
src/
  app/        # エントリ・全体の組み立て（main.tsx, App.tsx）
  pages/      # 画面単位
  widgets/    # 画面内の複合ブロック
  features/   # ユーザー操作の単位機能
  entities/   # ビジネス実体（タスク・予定 など）
  shared/     # 汎用（UIキット・lib・設定）
```

依存方向は上→下の一方通行（`app → pages → widgets → features → entities → shared`）。各レイヤーは `index.ts`（public API）経由で公開し、ESLint（boundaries）で違反を検出する。

## よく使うコマンド

`frontend/` ディレクトリで実行する。

```bash
npm install         # 依存インストール
npm run dev         # 開発サーバ（http://localhost:5173）
npm run build       # 型チェック + 本番ビルド（dist/）
npm run preview     # ビルド結果のプレビュー
npm run lint        # ESLint（FSD 境界チェック含む）
npm run format      # Prettier 整形
npm run format:check# 整形チェック
npm run typecheck   # 型チェック（tsc -b）
npm test            # テスト（Vitest）
```

## メモ

- Node はリポジトリ直下の `.nvmrc`（LTS）を想定。
- backend との API 連携は後続フェーズで追加する。
