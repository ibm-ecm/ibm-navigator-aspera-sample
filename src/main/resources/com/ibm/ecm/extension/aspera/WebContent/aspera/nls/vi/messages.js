/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

define({
		// NLS_CHARSET=UTF-8

		// configuration
		configuration_pane_aspera_url: "URL máy chủ IBM Aspera",
		configuration_pane_aspera_url_hover: "Nhập URL của máy chủ IBM Aspera. Ví dụ: https://host_name:port_number/aspera/faspex",
		configuration_pane_aspera_url_prompt: "Bạn nên sử dụng giao thức HTTPS protocol.",
		configuration_pane_max_docs_to_send: "Số lượng mục tối đa có thể gửi",
		configuration_pane_max_docs_to_send_hover: "Ðịnh rõ số lượng mục tối đa mà người dùng có thể gửi một lần.",
		configuration_pane_max_procs_to_send: "Số lượng yêu cầu đồng thời tối đa",
		configuration_pane_max_procs_to_send_hover: "Ðịnh rõ số lượng yêu cầu tối đa có thể chạy cùng một lúc.",
		configuration_pane_target_transfer_rate: "Tốc độ truyền tải đích (theo Mbps)",
		configuration_pane_target_transfer_rate_hover: "Ðịnh rõ tốc độ truyền tải đích theo megabit trên giây. Tốc độ này bị giới hạn theo giấy phép.",
		configuration_pane_speed_info: "Giấy phép hiện tại của bạn đang ở mức độ sơ cấp và dùng được 20 Mbps. Nâng cấp thành giấy phép đánh giá nhanh hơn (lên đến 10 Gbps) đối với Aspera Faspex bằng cách yêu cầu trên trang <a target='_blank' href='https://ibm.biz/BdjYHq'>Yêu cầu Đánh giá Aspera</a>.",

		// runtime
		send_dialog_sender_title: "Bên gửi: ${0}",
		send_dialog_not_set: "Không đặt",
		send_dialog_send_one: "Gửi '${0}'.",
		send_dialog_send_more: "Gửi ${0} tập tin.",
		send_dialog_sender: "Tên người dùng:",
		send_dialog_password: "Mật khẩu:",
		send_dialog_missing_sender_message: "Bạn phải nhập tên người dùng để đăng nhập vào máy chủ IBM Aspera.",
		send_dialog_missing_password_message: "Bạn phải nhập mật khẩu để đăng nhập vào máy chủ IBM Aspera.",
		send_dialog_title: "Gửi qua IBM Aspera",
		send_dialog_missing_title_message: "Bạn phải nhập tiêu đề.",
		send_dialog_info: "Gửi tập tin qua máy chủ IBM Aspera và thông báo cho người dùng rằng các tập tin đã có sẵn để tải xuống.",
		send_dialog_recipients_label: "Bên nhận:",
		send_dialog_recipients_textfield_hover_help: "Sử dụng dấu phẩy để tách địa chỉ email và/hoặc tên người dùng. Ví dụ, nhập 'địa_chỉ_1, địa_chỉ_2, tên_người_dùng_1, tên_người_dùng_2'.",
		send_dialog_missing_recipients_message: "Bạn phải định rõ ít nhất một địa chỉ email hoặc tên người dùng.",
		send_dialog_title_label: "Tiêu đề:",
		send_dialog_note_label: "Thêm thông báo.",
		send_dialog_earPassphrase_label: "Mật khẩu mã hóa:",
		send_dialog_earPassphrase_textfield_hover_help: "Nhập mật khẩu để mã hóa tập tin trên máy chủ. Sau đó, bên nhận sẽ cần phải nhập mật khẩu để giải mã tập tin được bảo vệ khi chúng được tải xuống.",
		send_dialog_notify_title: "Thông báo: ${0}",
		send_dialog_notifyOnUpload_label: "Thông báo cho tôi khi tập tin được tải lên",
		send_dialog_notifyOnDownload_label: "Thông báo cho tôi khi tập tin được tải xuống",
		send_dialog_notifyOnUploadDownload: "Thông báo cho tôi khi tập tin được tải lên và tải xuống",
		send_dialog_send_button_label: "Gửi",
		send_dialog_started: "Gói ${0} đang được gửi.",
		status_started: "Trạng thái gói: ${0} - Đang tiến hành (${1}%)",
		status_stopped: "Trạng thái gói: ${0} - Đã dừng lại",
		status_failed: "Trạng thái gói: ${0} - Thất bại",
		status_completed: "Trạng thái gói: ${0} - Hoàn tất",

		// error
		send_dialog_too_many_items_error: "Không thể gửi các mục này.",
		send_dialog_too_many_items_error_explanation: "Bạn có thể gửi tối đa ${0} mục một lần. Bạn đang cố gắng gửi ${1} mục.",
		send_dialog_too_many_items_error_userResponse: "Chọn ít mục hơn và cố gắng gửi lại các mục. Bạn cũng có thể liên hệ quản trị viên hệ thống của bạn và để tăng số lượng mục tối đa mà bạn có thể gửi một lần.",
		send_dialog_too_many_items_error_0: "maximum_number_of_items",
		send_dialog_too_many_items_error_1: "number_of_items",
		send_dialog_too_many_items_error_number: 5050,
});

