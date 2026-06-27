---
name: tidepo-design-doc
description: TiDepo repo専用。ラフな要件メモから、既存docsと実装文脈を踏まえ、曖昧点を質問しながら設計書を作成する。
---

## 入力

- ラフ要件メモのパス（なければ口頭要件）
- 既存設計書のパスがある場合はそのパス
- 出力先の希望がある場合はそのパス（既定は `docs/design/`）

## 基本方針

- 実装は行わない。
- Issue/PR作成は行わない。
- 既存docsや実装は必要範囲で読み、何を確認して何が分かったかを短く報告する。
- 曖昧点は推測で進めず、影響が大きいものから質問する。
- 質問は一度に詰め込みすぎず、設計の成否に効くものを優先する。
- 不明点があれば質問して解消し、未決事項をなくす。質問で確定できる項目は残さない。
- 現行実装、`CLAUDE.md`/`AGENTS.md`、`docs/requirements.md`、既存docsと矛盾しない設計にする。

## TiDepo前提

- バックエンド: Java / Spring Boot、オニオンアーキテクチャ（domain / application / infrastructure / presentation / config(DI)）。
- フロントエンド: TypeScript / React、FSD（app / pages / widgets / features / entities / shared）。
- コア価値は猶予時間（本当に残っている作業可能時間）の算出・可視化。計算ロジックは domain の純粋ロジックへ寄せる。
- Google Calendar 連携、認証（Googleログイン）、Docker、AWS、CI/CD を含みうる。

## 調査観点

- `docs/requirements.md`（要件定義書）
- `docs/design/`、`docs/plan/`（既存設計書・計画書）
- `CLAUDE.md` / `AGENTS.md`
- 関連する domain / application / infrastructure / presentation / config
- 関連 frontend の FSD 構成
- 既存の同種実装や同種docs
- 参考リポジトリ `todolistcalendar2`（旧実装。挙動・コアロジックの参照元）

## 手順

1. 入力されたラフ要件メモを読む。
2. 目的、利用者、入力、出力、業務制約、スコープ内外を仮整理する。
3. 既存docsと関連実装を必要範囲で調査し、調査プロセスと根拠を残す。
4. 高影響の曖昧点を質問し、回答を設計に反映する。
5. 複数案が成立する場合は、採用案、代替案、メリット/デメリット、将来破綻リスクを比較する。
6. 受入条件、テスト観点、影響範囲、未決事項を明確にする。
7. 設計書を `docs/design/` に作成または更新する。

## 設計書に入れる観点

- 背景・目的
- スコープ内/外
- 業務要件 / 受入条件
- 採用案と採用理由 / 検討した代替案 / メリット・デメリット / 将来破綻リスク
- データモデル（必要なら）
- レイヤー設計（Backend: domain/application/infrastructure/presentation/DI、Frontend: FSDスライス）
- 影響範囲
- テスト観点
- 未決事項

## 出力

- 作成/更新した設計書パス
- 調査した主要資料
- 採用案と採用理由
- 未決事項
- 次に実行すべき手順（通常は `tidepo-task-breakdown`）
