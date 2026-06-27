---
name: tidepo-pr-flow
description: TiDepo repo専用。PR作成、関連Issue紐付け、PR全体レビュー、必要時のsubagentレビュー修正ループまで進める。
---

## 入力

- 関連Issue番号
- base branch（既定 `main`）
- draft PR希望
- レビュー修正ループ上限。指定がなければ3回

## 基本方針

- リポジトリは `ogihaya/TiDepo`。GitHub操作は `gh` CLI を基本とし、利用可能なら GitHub MCP (`mcp__github__*`) を使ってもよい。
- 既存のユーザー変更を巻き戻さない。
- unrelated changeをPRに含めない。
- secret、credential、巨大ファイルを混ぜない。
- `git push` とPR作成は、ユーザー確認を待たずに実行する。push対象commitとPRタイトル/本文は提示する。
- PRコメント投稿などPRに公開コメントを残す操作は、実行直前にユーザー確認する。
- 既に同branchのPRがある場合は新規作成せず、既存PRを対象にする。
- main へ直接pushしない。

## 事前確認

1. `git status` で未コミット差分を確認する。
2. 現在branchを確認する。
3. base branch（既定 `main`）を確認する。
4. base branchからの全commit一覧を確認する。
5. base branchからの全diff/statを確認する。
6. 既存PRの有無を確認する。
7. 未push commitの有無を確認する。
8. 関連Issue、設計書、実装方針・手順書、詳細実装計画書があれば読む。

## PR作成方針

- 未push commitがある場合は、push対象commitを提示した上で `git push` する。
- PR作成直前に、タイトルと本文をユーザーに提示する（確認は待たない）。
- draft PR指定がある場合はdraftで作成する。
- 本文に `Closes #<Issue番号>` で関連Issueを紐付ける。

## PR本文テンプレート

```markdown
## 概要

## 変更内容

## 確認したこと

## 未実行・残課題

## 関連
Closes #
```

必要な場合だけ追加する:

- `## スクリーンショット`: UI変更時
- `## マイグレーション`: DB変更時
- `## レビュー観点`: 特に見てほしい箇所がある場合

## PRレビュー修正ループ

- レビューは `.claude/agents/tidepo-pr-reviewer.md`（Agent tool / `subagent_type: tidepo-pr-reviewer`）を使う。
- `review_round=1..3` を親エージェントが明示的に管理する。
- subagentへ渡す文脈が不足している状態でレビューを依頼しない。
- 修正必須があり、かつ `review_round < 3` なら修正、テスト、必要なcommitを行う（commitは `tidepo-commit`）。
- `review_round == 3` のレビュー結果を受け取ったら、追加修正せず停止する。
- 再レビューでは、前回指摘と対応内容を必ず渡す。

## subagentレビュー依頼に含める情報

- PR番号 / 関連Issue / 設計書 / 実装方針・手順書 / 詳細実装計画書
- PRの目的、完了条件、対象外、既知制約
- 対象commitまたはdiff範囲
- 実行済みテストと未実行テスト
- 既知の未解決事項
- 現在の `review_round` と最大回数

## 出力

- PR URL / base・head branch / commit一覧 / 変更概要
- 実行済みテスト / 未実行テストと理由
- レビュー結果 / 未解決指摘、採否判断、次の対応
