---
name: tidepo-issue-done
description: TiDepo repo専用。Issueの完了条件を確認し、明示確認後にIssue closeを行う。
---

Issueの完了判断を補助し、必要な場合だけ Issue close を行う。リポジトリは `ogihaya/TiDepo`。

## 基本方針

- GitHub操作は `gh` CLI を基本とし、利用可能なら GitHub MCP (`mcp__github__*`) を使ってもよい。
- Issue close は外部副作用なので、実行直前にユーザー確認する。
- 未実行テストや既知の残課題がある場合は、勝手に完了扱いにしない。
- PR merge前にIssueを閉じるか、merge後に閉じるか判断できない場合は確認する。

## 入力

- Issue番号
- 関連PR番号
- 完了判断に必要な追加資料

## 事前確認

1. Issue本文と受入条件を確認する。
2. 関連PR、関連commit、関連branchを確認する。
3. PRがmerge済みか確認する。
4. 実行済みテストを確認する。
5. 未実行テストと理由を確認する。
6. 残課題、既知リスク、follow-up Issueの有無を確認する。

## 停止条件

- 受入条件未達。
- 未完了手順が残っている。
- 関連PRが未mergeで、先にIssueを閉じてよい判断材料がない。
- 未実行テストや残課題が完了判断に影響する。
- close対象が特定できない。

## 承認境界

- Issue close直前にユーザー確認する。
- Issueへの完了コメント投稿など、GitHub状態を変える操作も直前確認する。

## 実行手順

1. Issueの受入条件と現在状態を整理する。
2. 関連PR/commit/test/残課題を照合する。
3. 完了可能か、保留すべきか判断する。
4. 完了可能な場合は、Issue closeの実行内容を提示する。
5. ユーザー確認後、`gh issue close {番号} --repo ogihaya/TiDepo`（必要なら完了コメント付き）で実行する。
6. 実行結果を確認する。

## 出力

- 完了判断
- Issue close結果
- 実行済みテスト / 未実行テストと理由
- 残課題またはfollow-up候補

## 注意

- GitHub Project を利用する場合のみ、ユーザー確認のうえ Status を Done に更新する（個人開発のため既定では不要）。
