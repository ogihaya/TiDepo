---
name: tidepo-review
description: TiDepo repo専用。ローカル差分またはPRを、正しさ、オニオン/FSD、責務、設計逸脱、テスト不足、セキュリティ観点でレビューする。
---

## 入力

- ローカル差分またはPR番号（リポジトリ `ogihaya/TiDepo`）
- 設計書または実装計画書のパス
- 手順単位レビューかPR全体レビューか

## 基本方針

- Findings firstで、重大度順にレビュー結果を出す。
- 文脈が不足している場合は、設計逸脱を断定せず「レビュー文脈不足」と明記する。
- 既存のユーザー変更を巻き戻さない。
- unrelated changeが混ざっていないか確認する。
- secret、credential、API key、各種クラウド鍵、個人情報の混入を確認する。
- PRをレビューする場合は `gh` CLI を基本とし、利用可能なら GitHub MCP を使ってもよい。
- PRコメント投稿などGitHub状態を変える操作は、実行直前にユーザー確認する。

## 調査手順

1. ローカル差分なら `git status`、`git diff`、必要に応じて base branch との差分を確認する。
2. PRならPR metadata、files、diff、CI/check状態、PR本文を確認する。
3. 関連Issue、設計書（`docs/design/`）、実装方針・手順書・詳細実装計画書（`docs/plan/`）、要件（`docs/requirements.md`）を読む。
4. 変更ファイルから backend / frontend / DB / docs / config のどれに該当するか判定する。
5. 同一directoryや同種機能の既存パターンを数件確認する。
6. 変更が目的と完了条件を満たすかレビューする。

## レビュー観点

### Backend（Java / Spring Boot / オニオン）

- オニオンの依存方向を破っていないか（依存は内向き。domain は他レイヤーに依存しない）。
- domain / application / infrastructure / presentation / config(DI) の責務が混ざっていないか。
- application 層が JPA エンティティ・DB操作・外部API（Google Calendar）を直接扱っていないか。
- Repository インターフェースと Infrastructure 実装が同時更新されているか。
- 猶予時間計算など、コアロジックが domain の純粋ロジックとして実装され、テスト可能か。
- 例外、型、命名、Javadoc、null安全、コメントがプロジェクト方針に沿っているか。

### Frontend（React / TypeScript / FSD）

- FSD の依存方向を破っていないか（上→下の一方向。下位は上位を import しない）。
- public API（index）越境、import 境界、状態管理が既存パターンに沿っているか。
- UI状態、loading / error / empty、権限、validation が不足していないか。
- 既存コンポーネントや同種画面のパターンから不自然に逸脱していないか。

### 共通

- 要件、受入条件、対象手順の完了条件を満たしているか。
- セキュリティリスク（認証/認可漏れ、機密情報ハードコード、入力未検証）がないか。
- テスト不足がないか。
- 不要な差分やunrelated changeが混ざっていないか。
- PRの場合、PR本文、scope、commit粒度がレビュー可能な状態か。

## 指摘分類

- Critical: 修正必須。バグ、セキュリティ、データ損失、重大な設計逸脱。
- Warning: 修正推奨。品質、保守性、規約違反、テスト不足。
- Suggestion: 検討推奨。より良い書き方や将来改善。
- Open Question: 要件判断が必要な確認事項。

## 出力

指摘がある場合:

```markdown
## Findings

### Critical
- **ファイル:行**
  - 問題:
  - 影響:
  - 修正方針:

### Warning
- ...

### Suggestion
- ...

### Open Questions
- ...

## Test Gaps

## Summary
```

指摘なしの場合:

- 「必須修正なし」と明記する。
- Test Gaps、Open Questions、Summaryを短く続ける。
