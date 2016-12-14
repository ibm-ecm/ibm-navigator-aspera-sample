/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

define({
		// NLS_CHARSET=UTF-8

		// configuration
		configuration_pane_aspera_url: "عنوان URL لوحدة خدمة IBM Aspera",
		configuration_pane_aspera_url_hover: "قم بادخال عنوان URL الخاص بوحدة خدمة IBM Aspera. على سبيل المثال:https://host_name:port_number/aspera/faspex",
		configuration_pane_aspera_url_prompt: "يوصى باستخدام بروتوكول HTTPS.",
		configuration_pane_max_docs_to_send: "الحد الأقصى لعدد البنود التي سيتم ارسالها",
		configuration_pane_max_docs_to_send_hover: "حدد الحد الأقصى لعدد البنود التي يستطيع المستخدمين ارسالها في كل مرة.",
		configuration_pane_max_procs_to_send: "الحد الأقصى لعدد الطلبات المتزامنة",
		configuration_pane_max_procs_to_send_hover: "حدد الحد الأقصى لعدد الطلبات التي يمكن تشغيلها في نفس الوقت.",
		configuration_pane_target_transfer_rate: "معدل النقل المستهدف (ميجابايت لكل ثانية)",
		configuration_pane_target_transfer_rate_hover: "حدد معدل النقل المستهدف بالميجابايت لكل ثانية. يتم تحديد المعدل وفقا للترخيص. ",
		configuration_pane_speed_info: "ترخيص مستوى الادخال الحالي الخاص بك يكون بالنسبة الى 20 ميجابايت لكل ثانية. قم بالترقية الى ترخيص تقييم أسرع (حتى 10 ميجابايت لكل ثانية) الى Aspera Faspex من خلال طلبه في صفحة <a target='_blank' href='https://ibm.biz/BdjYHq'>طلب تقييم Aspera</a>. ",

		// runtime
		send_dialog_sender_title: "المرسل: ${0}",
		send_dialog_not_set: "لم يتم التحديد",
		send_dialog_send_one: "ارسال '${0}'.",
		send_dialog_send_more: "ارسال ${0} ملف/ملفات.",
		send_dialog_sender: "اسم المستخدم:",
		send_dialog_password: "كلمة السرية:",
		send_dialog_missing_sender_message: "يجب عليك ادخال اسم المستخدم لبدء الاتصال بوحدة خدمة IBM Aspera.",
		send_dialog_missing_password_message: "يجب عليك اخال كلمة سرية لبدء الاتصال بوحدة خدمة IBM Aspera.",
		send_dialog_title: "ارسال من خلال IBM Aspera",
		send_dialog_missing_title_message: "يجب ادخال عنوان.",
		send_dialog_info: "يتم ارسال الملفات من خلال وحدة خدمة IBM Aspera واعلام المستخدمين بأن الملفات متاحة للتنزيل. ",
		send_dialog_recipients_label: "المستلمين:",
		send_dialog_recipients_textfield_hover_help: "استخدم فاصلة لفصل عناوين البريد الالكتروني و/أو أسماء المستخدمين. على سبيل المثال، أدخل 'address1، ‏address2، ‏username1، ‏username2'.",
		send_dialog_missing_recipients_message: "يجب تحديد عنوان بريد الكتروني أو اسم مستخدم واحد على الأقل.",
		send_dialog_title_label: "العنوان:",
		send_dialog_note_label: "اضافة رسالة.",
		send_dialog_earPassphrase_label: "تشفير كلمة السرية:",
		send_dialog_earPassphrase_textfield_hover_help: "أدخل كلمة سرية لتشفير الملفات على وحدة الخدمة. بعد ذلك، سيتطلب من المستلمين ادخال كلمة السرية لتشفير الملفات التي تم حمايتها عند تنزيلها.",
		send_dialog_notify_title: "الاعلام: ${0}",
		send_dialog_notifyOnUpload_label: "اعلامي عند تحميل الملف",
		send_dialog_notifyOnDownload_label: "اعلامي عند تنزيل الملف",
		send_dialog_notifyOnUploadDownload: "اعلامي عند تحميل وتنزيل الملف",
		send_dialog_send_button_label: "ارسال",
		send_dialog_started: "يتم ارسال مجموعة البرامج ${0}. ",
		status_started: "حالة مجموعة البرامج: ${0} - جاري التشغيل (${1}%)",
		status_stopped: "حالة مجموعة البرامج: ${0} - تم الايقاف",
		status_failed: "حالة مجموعة البرامج: ${0} - فشلت",
		status_completed: "حالة مجموعة البرامج: ${0} - تمت",

		// error
		send_dialog_too_many_items_error: "لا يمكن ارسال البنود.",
		send_dialog_too_many_items_error_explanation: "يمكنك ارسال حتى ${0} بند/بنود في كل مرة. تقوم بمحاولة ارسال ${1} بند/بنود.",
		send_dialog_too_many_items_error_userResponse: "حدد بنود أقل وحاول ارسال البنود مرة أخرى. يمكنك أيضا الاتصال بمسؤول النظام لزيادة الحد الأقصى لعدد البنود التي يمكنك ارسالها في كل مرة.",
		send_dialog_too_many_items_error_0: "maximum_number_of_items",
		send_dialog_too_many_items_error_1: "number_of_items",
		send_dialog_too_many_items_error_number: 5050,
});

