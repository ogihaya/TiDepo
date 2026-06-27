---
name: tidepo-commit
description: TiDepo repo専用。変更差分を確認し、unrelated changeを分離し、適切な粒度と日本語メッセージでコミットする。
---

## 基本方針

- TiDepo repoの変更を適切な粒度でcommitする。
- 既存のユーザー変更を巻き戻さない。
- unrelated changeを勝手に含めない。
- secret、credential、API key、token、秘密鍵、Google OAuthシークレット、AWS鍵、個人情報をcommitしない。
- binaryや巨大ファイルがある場合は確認する。
- pushはしない。pushはユーザーが明示した場合だけ別途確認して実行する。
- destructive git操作は行わない。

## 手順

1. `git status` を確認する。`-uall` は基本的に使わない。
2. staged / unstaged diffを確認する。
3. `git log -n 5 --oneline` で過去commit styleを確認する。
4. 変更ファイルを読み、unrelated changeが混ざっていないか確認する。
5. `.env`、credential、API key、token、秘密鍵、各種クラウド鍵、個人情報の混入を確認する。
6. binaryや巨大ファイルがある場合は、commit対象に含めてよいか確認する。
7. 関連テストまたは品質ゲートの実行状況を確認する。
8. 1 commit 1論点を基準にcommit粒度を判断する。
9. commit messageを日本語で作る。
10. ユーザー承認は待たず、commit messageを提示した上で、具体file指定で `git add` してcommitする。
11. commit後に `git status` とcommit hashを確認する。

## commit message方針

- 日本語で書く。
- 1行目は変更内容を短く書く。
- 必要なら本文に「なぜその変更が必要か」を書く。
- Conventional Commit prefixは、TiDepoの既存styleに合う場合のみ任意で使う。
- AI生成フッターやCo-Authored-Byは付けない。

## 禁止・注意

- `git add .` は使わない。`git add -A` は使わない。
- `--no-verify` でhook失敗を回避しない。
- pre-commitやtestが失敗した場合は原因を確認し、未解決ならcommitしない。
- 複数論点が混ざっている場合は、確認を待たずに論点ごとにcommitを分割する。
- user-level設定やrepo-local private設定をcommit対象に含める場合は、git管理対象かどうかを確認する。

## 出力

- commit hash / commit message / commit対象ファイル
- 除外したunrelated change
- 実行済みテスト / 未実行テストと理由
