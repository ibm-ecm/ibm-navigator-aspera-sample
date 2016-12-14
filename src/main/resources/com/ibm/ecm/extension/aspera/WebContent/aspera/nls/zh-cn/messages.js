/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

define({
		// NLS_CHARSET=UTF-8

		// configuration
		configuration_pane_aspera_url: "IBM Aspera 服务器 URL",
		configuration_pane_aspera_url_hover: "请输入 IBM Aspera 服务器的 URL。例如：https://host_name:port_number/aspera/faspex",
		configuration_pane_aspera_url_prompt: "强烈建议使用 HTTPS 协议。",
		configuration_pane_max_docs_to_send: "要发送的最大项数",
		configuration_pane_max_docs_to_send_hover: "指定用户一次可发送的最大项数。",
		configuration_pane_max_procs_to_send: "最大并行请求数",
		configuration_pane_max_procs_to_send_hover: "指定可同时运行的最大请求数。",
		configuration_pane_target_transfer_rate: "目标传输速率 (Mbps)",
		configuration_pane_target_transfer_rate_hover: "指定目标传输速率（兆字节/秒）。速率受到许可证的限制。",
		configuration_pane_speed_info: "当前的入门级许可证允许的速率为 20 Mbps。通过在 <a target='_blank' href='https://ibm.biz/BdjYHq'>Aspera 评估请求</a>页面上请求速率为 Aspera Faspex 升级到更快的评估许可证（最多可达 10 Gbps）。",

		// runtime
		send_dialog_sender_title: "发件人：${0}",
		send_dialog_not_set: "未设置",
		send_dialog_send_one: "发送“${0}”。",
		send_dialog_send_more: "发送 ${0} 个文件。",
		send_dialog_sender: "用户名：",
		send_dialog_password: "密码：",
		send_dialog_missing_sender_message: "必须输入用户名以登录 IBM Aspera 服务器。",
		send_dialog_missing_password_message: "必须输入密码以登录 IBM Aspera 服务器。",
		send_dialog_title: "通过 IBM Aspera 发送",
		send_dialog_missing_title_message: "必须输入标题。",
		send_dialog_info: "通过 IBM Aspera 服务器发送文件，并通知用户该文件可供下载。",
		send_dialog_recipients_label: "收件人：",
		send_dialog_recipients_textfield_hover_help: "使用逗号来分隔电子邮件地址和/或用户名。例如，输入“address1, address2, username1, username2”。",
		send_dialog_missing_recipients_message: "必须指定至少一个电子邮件地址或用户名。",
		send_dialog_title_label: "标题：",
		send_dialog_note_label: "添加消息。",
		send_dialog_earPassphrase_label: "加密密码：",
		send_dialog_earPassphrase_textfield_hover_help: "输入密码以在服务器上加密这些文件。然后，系统会在收件人下载受保护文件时要求他们输入密码以解密这些文件。",
		send_dialog_notify_title: "通知：${0}",
		send_dialog_notifyOnUpload_label: "上载该文件时通知我",
		send_dialog_notifyOnDownload_label: "下载该文件时通知我",
		send_dialog_notifyOnUploadDownload: "上载和下载该文件时通知我",
		send_dialog_send_button_label: "发送",
		send_dialog_started: "正在发送包 ${0}。",
		status_started: "包状态：${0} - 正在进行 (${1}%)",
		status_stopped: "包状态：${0} - 已停止",
		status_failed: "包状态：${0} - 已失败",
		status_completed: "包状态：${0} - 已完成",

		// error
		send_dialog_too_many_items_error: "无法发送这些项。",
		send_dialog_too_many_items_error_explanation: "一次最多可发送 ${0} 项。您正尝试发送 ${1} 项。",
		send_dialog_too_many_items_error_userResponse: "请选择较少项，然后重试发送这些项。您还可联系系统管理员以增加一次可发送的最大项数。",
		send_dialog_too_many_items_error_0: "最大项数",
		send_dialog_too_many_items_error_1: "项数",
		send_dialog_too_many_items_error_number: 5050,
});

