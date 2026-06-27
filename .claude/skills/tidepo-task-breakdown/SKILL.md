---
name: tidepo-task-breakdown
description: TiDepo repo専用。設計書から、1回のAI実行に適切な粒度の実装方針・手順書とIssue登録案を作成する。
---

## 入力

- 設計書のパス（`docs/design/` 配下）
- 既存の実装方針・手順書がある場合はそのパス（既定の出力先は `docs/plan/`）
- Issue作成を明示する追加指示がある場合はその旨

## 基本方針

- 既定ではGitHub Issue作成は実行しない。Issue登録案までを作る。
- 実際のIssue作成は、ユーザーの明示指示と直前確認がある場合だけにする（その際は `tidepo-issue` を使う）。
- 手順は1回のAI実行に適切な粒度へ分ける。
- docs/config作業、backend実装、frontend実装、DB変更、Google Calendar連携、CI/Docker を混ぜすぎない。
- 依存関係、完了条件、受入条件、テスト観点を手順ごとに明確にする。

## 分解観点

- 純粋ロジックや domain から外側へ進める、TDDフレンドリーな順序。
- Backend: domain / application / infrastructure / presentation / config(DI) / test の境界。
- Frontend: 型 / API client / hooks(features) / entities / UI(widgets) / page統合 の境界（FSD）。
- DB変更、API変更、Frontend変更、Google Calendar連携がまたがる場合の分割可能性。
- 依存関係 / 完了条件 / 受入条件 / テスト観点 / 影響範囲 / リスクと保留条件。
- Issue化する場合の粒度。

## 手順

1. 設計書を読む。
2. 既存実装、既存テスト、関連docsの配置を確認する。
3. 設計のスコープ内/外、変更境界、リスクを整理する。
4. 1回のAI実行に適切な粒度で手順へ分解する。
5. 各手順について、目的、やること、やらないこと、成果物、受入条件、検証を記述する。
6. 必要に応じてIssue登録案を作る。ただし作成は実行しない。
7. 実装方針・手順書を `docs/plan/` に作成または更新する。

## 実装方針・手順書に入れる観点

- 目的 / 前提 / 分割方針
- 手順一覧
- 各手順の 目的 / やること / やらないこと / 成果物 / 受入条件 / 検証
- 推奨実行順序
- リスクと注意点

## Issue登録案

Issue化する場合は、以下を登録案として作る（作成は `tidepo-issue` で）。

- Issueタイトル / 背景 / やること / やらないこと / 受入条件 / 実装メモ / テスト観点 / 関連資料

## 出力

- 作成/更新した実装方針・手順書パス
- 手順数 / 依存関係
- Issue登録案
- 実際のIssue作成は未実行であること
- 次に実行すべき手順（通常は `tidepo-step-plan`）
