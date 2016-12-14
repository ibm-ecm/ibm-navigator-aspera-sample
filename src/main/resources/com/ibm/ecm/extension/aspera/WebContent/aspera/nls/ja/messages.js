/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

define({
		// NLS_CHARSET=UTF-8

		// configuration
		configuration_pane_aspera_url: "IBM Aspera サーバー URL",
		configuration_pane_aspera_url_hover: "IBM Aspera サーバーの URL を入力します。 例: https://host_name:port_number/aspera/faspex",
		configuration_pane_aspera_url_prompt: "HTTPS プロトコルを使用することを強くお勧めします。",
		configuration_pane_max_docs_to_send: "送信するアイテムの最大数",
		configuration_pane_max_docs_to_send_hover: "ユーザーが一度に送信できるアイテムの最大数を指定します。",
		configuration_pane_max_procs_to_send: "並行要求の最大数",
		configuration_pane_max_procs_to_send_hover: "同時に実行できる要求の最大数を指定します。",
		configuration_pane_target_transfer_rate: "ターゲット転送速度 (Mbps)",
		configuration_pane_target_transfer_rate_hover: "ターゲット転送速度を 1 秒あたりのメガビット数で指定します。この速度には、ライセンスに基づく制限があります。",
		configuration_pane_speed_info: "現在のエントリー・レベルのライセンスは 20 Mbps に対応しています。Aspera Faspex の高速の評価ライセンス (最大 10 Gbps) にアップグレードできます。<a target='_blank' href='https://ibm.biz/BdjYHq'>Aspera Evaluation Request</a> のページでリクエストを送ってください。",

		// runtime
		send_dialog_sender_title: "送信者: ${0}",
		send_dialog_not_set: "未設定",
		send_dialog_send_one: "'${0}' を送信します。",
		send_dialog_send_more: "${0} ファイルを送信します。",
		send_dialog_sender: "ユーザー名:",
		send_dialog_password: "パスワード:",
		send_dialog_missing_sender_message: "IBM Aspera サーバーにログインするには、ユーザー名を入力する必要があります。",
		send_dialog_missing_password_message: "IBM Aspera サーバーにログインするには、パスワードを入力する必要があります。",
		send_dialog_title: "IBM Aspera 経由の送信",
		send_dialog_missing_title_message: "タイトルを入力する必要があります。",
		send_dialog_info: "IBM Aspera サーバー経由でファイルを送信し、ファイルをダウンロードできることをユーザーに通知します。",
		send_dialog_recipients_label: "受信者",
		send_dialog_recipients_textfield_hover_help: "複数の E メール・アドレスおよび/またはユーザー名は、コンマで区切ります。 例えば、'address1, address2, username1, username2' のように入力します。",
		send_dialog_missing_recipients_message: "少なくとも 1 つの E メール・アドレスまたはユーザー名を指定する必要があります。",
		send_dialog_title_label: "タイトル:",
		send_dialog_note_label: "メッセージを追加します。",
		send_dialog_earPassphrase_label: "暗号化パスワード:",
		send_dialog_earPassphrase_textfield_hover_help: "サーバー上のファイルを暗号化するためのパスワードを入力します。 後に受信者は保護されたそれらのファイルをダウンロードする際、ファイルを暗号化解除するためにそのパスワードを入力する必要があります。",
		send_dialog_notify_title: "通知: ${0}",
		send_dialog_notifyOnUpload_label: "ファイルのアップロード時に通知",
		send_dialog_notifyOnDownload_label: "ファイルのダウンロード時に通知",
		send_dialog_notifyOnUploadDownload: "ファイルのアップロード時とダウンロード時に通知",
		send_dialog_send_button_label: "送信",
		send_dialog_started: "パッケージ ${0} の送信中です。",
		status_started: "パッケージ状況: ${0} - 進行中 (${1}%)",
		status_stopped: "パッケージ状況: ${0} - 停止",
		status_failed: "パッケージ状況: ${0} - 失敗",
		status_completed: "パッケージ状況: ${0} - 完了",

		// error
		send_dialog_too_many_items_error: "アイテムを送信できません。",
		send_dialog_too_many_items_error_explanation: "一度に送信できる最大アイテム数は ${0} 個です。 ${1} 個のアイテムを送信しようとしています。",
		send_dialog_too_many_items_error_userResponse: "選択アイテムを減らして、アイテムの送信を再試行してください。 システム管理者に連絡して、一度に送信できるアイテムの最大数を増やしてもらうこともできます。",
		send_dialog_too_many_items_error_0: "最大アイテム数",
		send_dialog_too_many_items_error_1: "アイテム数",
		send_dialog_too_many_items_error_number: 5050,
});

