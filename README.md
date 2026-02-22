# Denda's Bank
簡単な銀行取引を体験を提供するために開発されたSpring Bootアプリケーションです。

## 目次

- [プロジェクト概要](#プロジェクト概要)
- [主な機能](#主な機能)
- [使用技術](#使用技術)
- [API Endpoints](#api-endpoints)
- [実行方法](#実行方法)
- [使い方](#使い方)
- [](#)
- [写真](#写真)

## プロジェクト概要
簡単な銀行取引を体験を提供するために開発されたSpring Bootアプリケーションです。

## 主な機能
- ユーザー登録・認証 : BCryptによるパスワード暗号化、セッションベースの認証管理
- 口座 : 口座の作成、残高確認、一覧表示、振り込み
- 取引履歴の閲覧

## 使用技術
- Backend : Java 17, Spring Boot 4.0
- Database : PostgreSQL
- ORM : Mybatis 4.0
- Build Tool : Maven
- Test : Junit
- Frontend : HTML, Bootstrap(CSS)
- Thymeleaf

## API Endpoints

ユーザー
- POST /login
- POST /register
- POST /logout

口座
- POST /account/createAccount
- POST /account/{accountNumber}/transfer
- GET /account/{accountId}/history

ダッシュボード
- GET /app/dashboard

## 実行方法

1. リポジトリをクローン
```sh
git clone https://github.com/denda21/bankApp.git
```

2. PostgreSQL データベースの作成
   bank_app という名前のデータベースを作成してください
   update the database configuration in src/main/resources/application.properties

3. プロジェクトビルド
```sh
mvn clean install
```

4. アプリケーションの起動
```sh
mvn spring-boot:run
```

## 使い方

- Webブラウザで `http://127.0.0.1:8076/` にアクセス
- 会員登録でアカウントを作成
- ログインでダッシュボードに移動
- ダッシュボード**で口座を作成し、残高を管理
- 送金で他の口座に送金
- 取引履歴で過去の送金・入金記録を確認

## エンジニアリング、トラブルシューティング


## 写真
