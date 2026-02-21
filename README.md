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



## トランザクション・同時実行制御・悲観的ロック

test

## トラブルシューティング

test

## テストコード・デバッグ・エラーハンドリング・ロギング

### テストコード

test

## 実行方法

### Maven で実行

```bash
# 1. リポジトリをクローン
git clone https://github.com/your-username/bank_project.git
cd bank_project

# 2. PostgreSQL データベースの作成
# bank_app という名前のデータベースを作成してください
createdb bank_app

# 3. データベース設定
# src/main/resources/application.properties を環境に合わせて編集
#   spring.datasource.url=jdbc:postgresql://localhost:5432/bank_app
#   spring.datasource.username=postgres
#   spring.datasource.password=postgres

# 4. プロジェクトのビルド
mvn clean install

# 5. アプリケーションの起動
mvn spring-boot:run
```


## 使い方

1. Webブラウザで `http://127.0.0.1:8076/` にアクセス
2. **会員登録**（`/register`）でアカウントを作成
3. **ログイン**（`/login`）でダッシュボードに移動
4. **ダッシュボード**で口座を作成し、残高を管理
5. **送金**で他の口座に送金
6. **取引履歴**で過去の送金・入金記録を確認

## 写真