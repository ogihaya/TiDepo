---
name: tidepo-issue
description: TiDepo repo専用。説明文やIssue登録案からGitHub Issueを作成する。
allowed-tools: Bash(gh issue *), Bash(gh project *)
---

ユーザーの説明文（または `tidepo-task-breakdown` のIssue登録案）から適切なGitHub Issueを作成する。リポジトリは `ogihaya/TiDepo`。

## 手順

1. 説明文を分析し、以下を判断する:
   - **種別**: feature / bug / refactor / docs / chore / qa
   - **対象領域**: backend / frontend / db / auth / google-calendar / infra / ci（該当すれば）
   - **優先度**: high / medium / low（明示されていなければ medium）

2. Issueタイトルを作成する（短く具体的に・日本語・何をするか一目でわかる）。

3. Issue本文を作成する:
   - `## やりたいこと` — 意図を整理して書く
   - `## 背景` — なぜ必要か
   - `## 想定スコープ` — 対象ファイル・レイヤー・作業内容（わかる範囲で）
   - `## 受入条件` — 完了の判断基準
   - `## テスト観点`
   - 不明な部分は無理に埋めず「要件の詳細化が必要」と記す

4. ラベルを選択する（リポジトリに存在するもののみ。なければ作成は任意でユーザー確認）:
   - 種別: `type:feature` / `type:bug` / `type:refactor` / `type:docs` / `type:chore` / `type:qa`
   - 領域: `area:backend` / `area:frontend` / `area:db` / `area:auth` / `area:google-calendar` / `area:infra` / `area:ci`
   - 優先度: `priority:high` / `priority:medium` / `priority:low`

5. AskUserQuestion で確認する（タイトル・ラベル・本文のプレビューを提示。OKなら作成、修正があれば反映）。

6. 確認後 `gh issue create --repo ogihaya/TiDepo ...` で作成する。

7. 作成したIssueのURLを返す。

## 注意

- 説明文が曖昧な場合は、わかる範囲で書いて「要件の詳細化が必要」と本文に記載する。
- 1つの説明文に複数のIssueが含まれる場合は分割して作成する。
- 関連するIssueがあれば本文で `#番号` で参照する。
- GitHub Project を利用する場合のみ、作成後にユーザー確認のうえ `gh project item-add` で追加する（個人開発のため既定では不要）。
