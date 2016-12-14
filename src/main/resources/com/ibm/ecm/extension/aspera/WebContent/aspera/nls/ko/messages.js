/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

define({
		// NLS_CHARSET=UTF-8

		// configuration
		configuration_pane_aspera_url: "IBM Aspera 서버 URL",
		configuration_pane_aspera_url_hover: "IBM Aspera 서버의 URL을 입력하십시오. 예를 들면, https://host_name:port_number/aspera/faspex입니다.",
		configuration_pane_aspera_url_prompt: "HTTPS 프로토콜을 사용하는 것이 좋습니다.",
		configuration_pane_max_docs_to_send: "송신할 최대 항목 수",
		configuration_pane_max_docs_to_send_hover: "사용자가 한 번에 송신할 수 있는 최대 항목 수를 지정하십시오. ",
		configuration_pane_max_procs_to_send: "최대 동시 요청 수",
		configuration_pane_max_procs_to_send_hover: "동시에 실행할 수 있는 최대 요청 수를 지정하십시오. ",
		configuration_pane_target_transfer_rate: "대상 전송률(Mbps)",
		configuration_pane_target_transfer_rate_hover: "대상 전송률(Mbps)을 지정하십시오. 이 비율은 라이센스로 제한됩니다.",
		configuration_pane_speed_info: "현재 시작 레벨 라이센스는 20Mbps용입니다. <a target='_blank' href='https://ibm.biz/BdjYHq'>Aspera Evaluation Request</a> 페이지에서 Aspera Faspex에 대한 더 빠른 평가 라이센스(최대 10Gbps)로의 업그레이드를 요청하십시오.",

		// runtime
		send_dialog_sender_title: "송신자: ${0}",
		send_dialog_not_set: "설정되지 않음",
		send_dialog_send_one: "'${0}'을(를) 전송하십시오.",
		send_dialog_send_more: "${0} 파일을 전송하십시오.",
		send_dialog_sender: "사용자 이름:",
		send_dialog_password: "비밀번호:",
		send_dialog_missing_sender_message: "IBM Aspera 서버에 로그인하려면 사용자 이름을 입력해야 합니다.",
		send_dialog_missing_password_message: "IBM Aspera 서버에 로그인하려면 비밀번호를 입력해야 합니다.",
		send_dialog_title: "IBM Aspera를 통해 송신",
		send_dialog_missing_title_message: "제목을 입력해야 합니다.",
		send_dialog_info: "IBM Aspera 서버를 통해 파일을 송신하고 사용자에게 파일을 다운로드할 수 있음을 알립니다.",
		send_dialog_recipients_label: "수신인:",
		send_dialog_recipients_textfield_hover_help: "쉼표를 사용하여 이메일 주소 및/또는 사용자 이름을 구분하십시오. 예를 들어, 'address1, address2, username1, username2'를 입력하십시오.",
		send_dialog_missing_recipients_message: "이메일 주소 또는 사용자 이름을 하나 이상 지정해야 합니다.",
		send_dialog_title_label: "제목:",
		send_dialog_note_label: "메시지 추가.",
		send_dialog_earPassphrase_label: "암호화 비밀번호:",
		send_dialog_earPassphrase_textfield_hover_help: "서버에서 파일을 암호화하려면 비밀번호를 입력하십시오. 그런 다음 파일을 다운로드할 때 보호된 파일을 복호화하려면 수신인은 비밀번호를 입력해야 합니다.",
		send_dialog_notify_title: "알림: ${0}",
		send_dialog_notifyOnUpload_label: "파일이 업로드되면 나에게 알림",
		send_dialog_notifyOnDownload_label: "파일이 다운로드되면 나에게 알림",
		send_dialog_notifyOnUploadDownload: "파일이 업로드되고 다운로드되면 나에게 알림",
		send_dialog_send_button_label: "송신",
		send_dialog_started: "${0} 패키지를 송신하는 중입니다.",
		status_started: "패키지 상태: ${0} - 진행 중(${1}%)",
		status_stopped: "패키지 상태: ${0} - 중지됨",
		status_failed: "패키지 상태: ${0} - 실패함",
		status_completed: "패키지 상태: ${0} - 완료됨",

		// error
		send_dialog_too_many_items_error: "항목을 송신할 수 없습니다.",
		send_dialog_too_many_items_error_explanation: "한 번에 최대 ${0}개의 항목을 송신할 수 있습니다. ${1}개의 항목을 송신하려고 합니다.",
		send_dialog_too_many_items_error_userResponse: "더 적은 항목을 선택하여 다시 송신하십시오. 또한 시스템 관리자에게 문의하여 한 번에 송신할 수 있는 항목의 최대 수를 늘릴 수 있습니다.",
		send_dialog_too_many_items_error_0: "maximum_number_of_items",
		send_dialog_too_many_items_error_1: "number_of_items",
		send_dialog_too_many_items_error_number: 5050,
});

