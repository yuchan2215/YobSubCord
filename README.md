# 動作環境
java 17.0.2以降 (17.0.1はシングルコア環境で正常動作しない不具合が確認されています)

# install
`/var/tapioca/`にて git clone を実行します。そうすると、`/var/tapioca/YobSubCord/`ディレクトリが作成されます。

# build
`YobSubCord`ディレクトリ内で`sh ./gradlew build`と実行します。そうすると`/var/tapipca/YobSubCord/build/libs/YobSubCord....jar/`が作成されます

# サービス登録
Ubuntu 20.0.4の場合は、サービスとして登録することによって`systemctl`コマンドによって実行することが可能となります。
`sudo sh regist.sh`を実行することによって登録ができます。


# ENVファイルの作成
次の内容でenvファイル(.env)を作成します。
サービス登録してある場合には、.envファイルを `/var/tapioca/.env`と配置してください。
```dotenv
DISCORDTOKEN= # TOKEN HERE #
ADMINROLE= # 管理者のロールid #
ALERTROLE= # チャンネル内でメンションするロールID #
DMALERTROLE= # DMで通知するロールID 空白で無効 #
ALERTCHANNEL = # 通知するDiscordチャンネルID #
YTCHANNELS = # 通知するYouTubeのチャンネルid , 区切り #
URL = # PubSubを受信するドメイン #
```

# LICENSES
[このプロジェクトのライセンス](LICENSE)
このプロジェクトを利用する場合は、厳しいライセンス事項がありますので確認してください。

 - jackson-module-kotlin
   - [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
 - jackson-module-kotlin
   - [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
 - kotlin-reflect
   - [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
 - kotlin-stdlib-jdk8
   - [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
 - spring-boot-starter-test
   - [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
 - gradle
   - [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
 - dependency-management-plugin
   - [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
 - dotenv-kotlin
   - [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
 - JDA
   - [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
 - Git-Version Gradle Plugin
   - [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
 - Apache Groovy
   - [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
 - JDK17
   - [Oracle No-Fee Terms and Conditions (NFTC)](https://www.oracle.com/downloads/licenses/no-fee-license.html)