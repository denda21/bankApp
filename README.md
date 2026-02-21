# Spring Boot 銀行Webアプリケーション

## プロジェクト概要

ユーザーにシームレスなバンキング体験を提供するために設計されたSpring Bootアプリケーションです。

## 主な機能
- ユーザー登録・認証 : BCryptによるパスワード暗号化、セッションベースの認証管理
- 口座管理 : 口座の作成、残高確認、一覧表示（ページネーション対応）
- 口座間送金 : synchronized方式 + 悲観的ロック（Pessimistic Lock）による同時実行制御
- 取引履歴 : 送金・入金履歴の閲覧（ページネーション対応）
- エラーハンドリング : `@ControllerAdvice`による一括例外処理


## 使用技術

- Backend : Java 17, Spring Boot 4.0
- Maven
- Junit
- Frontend : HTML, Bootstrap(CSS)
- データベース : PostgreSQL
- ORM : Mybatis 4.0
- Thymeleaf

## API Endpoints

### 認証（Auth）

| メソッド | エンドポイント | 説明 |
|---------|--------------|------|
| `GET`  | `/login` | ログインページ表示 |
| `POST` | `/login` | ログイン処理（email, password） |
| `GET`  | `/register` | 会員登録ページ表示 |
| `POST` | `/register` | 会員登録処理（user_name, email, password） |
| `POST` | `/logout` | ログアウト（セッション無効化） |

### 口座（Account）

| メソッド | エンドポイント | 説明 |
|---------|--------------|------|
| `POST` | `/account/createAccount` | 口座作成（account_name） |
| `POST` | `/account/{accountNumber}/transfer` | 口座間送金 |
| `GET`  | `/account/{accountId}/history` | 取引履歴照会（ページネーション対応） |

### ダッシュボード

| メソッド | エンドポイント | 説明 |
|---------|--------------|------|
| `GET`  | `/app/dashboard` | ユーザーダッシュボード（口座一覧表示） |

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