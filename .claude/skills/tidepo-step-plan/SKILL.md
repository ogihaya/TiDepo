---
name: tidepo-step-plan
description: TiDepo repo専用。実装方針・手順書から、指定された1手順の詳細実装計画書を作成する。Plan Modeと併用する場合は実装しない。
---

## 入力

- 実装方針・手順書のパス（`docs/plan/` 配下）
- 対象手順番号
- 関連設計書のパス（`docs/design/` 配下）
- 関連Issue番号がある場合はその番号

## 基本方針

- 詳細実装計画書の作成を主目的にする。出力先は既定で `docs/plan/`。
- Plan Modeで呼ばれた場合は、実装やファイル編集をしない。
- Plan Mode後にユーザーが「実装を進めて」と言った場合も、文脈上は詳細実装計画書作成を実行するだけであり、対象手順の実装ではない。
- Issue更新などの外部副作用は既定では行わない。

## 探索方針

- 先に設計書、実装方針・手順書、既存実装/設定を読む。
- repo/systemから分かることは質問せず探索で解消する。
- 高影響の未決事項は必ず質問する（スコープ、成功条件、副作用許容、出力先、検証方法）。
- 不明点があれば質問して解消し、未決事項をなくす。質問で確定できる項目は残さない。

## 手順

1. 実装方針・手順書と対象手順を読む。
2. 関連設計書、既存docs、既存実装/設定を必要範囲で読む。
3. 対象手順の目的、完了条件、対象外、既知制約を整理する。
4. 変更予定を Backend（domain / application / infrastructure / presentation / config(DI) / test）と Frontend（FSDスライス / API / hooks / UI / page）と Docs/Ops に分ける。
5. 変更予定ファイルを列挙する。
6. 高影響の未決事項がある場合は質問する。
7. テスト方針と品質ゲートを明記する。
8. 詳細実装計画書を作成または更新する。

## 詳細実装計画書の出力項目

- 対象Issue / 対象手順 / 目的 / 完了条件
- 変更予定ファイル
- レイヤー別実装方針（Backend オニオン / Frontend FSD）
- 実行方針 / テスト方針 / 品質ゲート
- 未決事項 / 実装開始前確認

## 粒度方針

- 1回のAI実行で完了できる手順にする。
- backend / frontend / DB / ops / docs の変更境界を明記する。
- docs/configのみの手順では、アプリケーション品質ゲートを実行しない理由を明記する。

## 品質ゲート基準（プロジェクトのbuild設定に従う）

- Backend（Gradle）: `./gradlew spotlessApply`（整形）→ `./gradlew check`（静的解析）→ ArchUnitによるオニオン依存検証（`./gradlew test` または専用タスク）→ `./gradlew test`（テスト）。
- Frontend: `npm run lint` → `npm run typecheck`（`tsc --noEmit`）→ `npm test`（Vitest）→ `npm run build`。
- 具体的なタスク名・script名は scaffold 後のプロジェクト設定に合わせる。

## Plan Modeで呼ばれた場合

- 実装やファイル編集はしない。詳細実装計画書の作成計画までに留める。
- 公式の最終計画は ExitPlanMode で提示する。
- Issue更新などの副作用は行わない。

## 出力

- 作成/更新した詳細実装計画書パス
- 未決事項
- 次に実行する場合の注意（通常は `tidepo-step-dev`）
