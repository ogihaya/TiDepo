---
name: tidepo-merge
description: TiDepo repo専用。PRを最終確認後にmergeし、関連Issueを必要に応じてcloseする。
---

PR番号を受け取り、最終確認後にmergeして関連Issueを更新する。リポジトリは `ogihaya/TiDepo`。

## 基本方針

- GitHub操作は `gh` CLI を基本とし、利用可能なら GitHub MCP (`mcp__github__*`) を使ってもよい。
- merge、Issue close は GitHub状態を変える操作なので、実行直前にユーザー確認する。
- CI未通過、未解決レビュー、受入条件未達がある場合はmergeしない。
- 既存のユーザー変更を巻き戻さない。destructive git操作は行わない。

## 入力

- PR番号またはPR URL
- 関連Issue番号
- merge後のlocal branch整理方針

## 事前確認

1. PR状態、title、body、base/head branch を確認する。
2. CI/check結果（`gh pr checks`）を確認する。
3. unresolved review thread や requested changes が残っていないか確認する。
4. 関連Issueの受入条件を確認する。
5. 関連commitと変更概要を確認する。
6. 未実行テスト、残課題、既知リスクを確認する。
7. merge方式（merge / squash / rebase）を確認する。

## 停止条件

- CI/checkが失敗している。
- 未解決レビューが残っている。
- 受入条件未達。
- 未実行テストがあり、ユーザー判断なしにmergeすると危険。
- PR scope外の差分や secret 混入が見つかった。
- merge対象や関連Issueが特定できない。

## 承認境界

- merge直前にユーザー確認する。
- Issue close直前にユーザー確認する。
- local branch削除や base branch最新化は確認してから行う。

## 実行手順

1. 事前確認の結果を短く整理する。
2. mergeしてよい状態か判断する。
3. ユーザーにmerge直前確認を取る。
4. 承認後、`gh pr merge {番号} --repo ogihaya/TiDepo --squash`（または指定方式）でmergeする。
   - PR本文に `Closes #N` があれば、merge時にIssueは自動closeされる。自動closeされない場合のみ手動closeを検討する。
5. 関連Issueのclose要否を確認し、必要なら直前確認後に `gh issue close` する。
6. local branch整理が必要な場合は、確認後に `git checkout main && git pull origin main` などを行う。

## 出力

- merge結果 / merge commit または squash commit
- Issue close結果
- local branch整理結果
- 未実行テストと残課題

## 注意

- PRがOpen以外の場合はmergeしない。
- 紐づくIssueが見つからない場合はmergeのみ行い、その旨を報告する。
- GitHub Project を利用する場合のみ、ユーザー確認のうえ Status を Done に更新する（個人開発のため既定では不要）。
