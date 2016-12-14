/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

define({
		// NLS_CHARSET=UTF-8

		// configuration
		configuration_pane_aspera_url: "URL адрес на сървъра на IBM Aspera",
		configuration_pane_aspera_url_hover: "Въведете URL на сървъра на IBM Aspera. Например: https://host_name:port_number/aspera/faspex",
		configuration_pane_aspera_url_prompt: "Силно препоръчително е да използвате протокола HTTPS.",
		configuration_pane_max_docs_to_send: "Максимален брой елементи за изпращане",
		configuration_pane_max_docs_to_send_hover: "Посочете максималния брой елементи, които потребителите могат да изпращат едновременно.",
		configuration_pane_max_procs_to_send: "Максимален брой едновременни заявки",
		configuration_pane_max_procs_to_send_hover: "Посочете максималния брой заявки, които могат да се изпълняват едновременно.",
		configuration_pane_target_transfer_rate: "Целева скорост на трансфер (в Mbps)",
		configuration_pane_target_transfer_rate_hover: "Посочете целевата скорост на трансфер в мегабита в секунда. Скоростта е ограничена от лиценза.",
		configuration_pane_speed_info: "Вашият текущ лиценз за начално ниво е за 20 Mbps. Надстройте до по-бърз лиценз за оценка (до 10 Gbps) за Aspera Faspex, като направите заявка на страницата на <a target='_blank' href='https://ibm.biz/BdjYHq'>Заявка за оценка на Aspera</a>.",

		// runtime
		send_dialog_sender_title: "Подател: ${0}",
		send_dialog_not_set: "Не е зададено",
		send_dialog_send_one: "Изпрати '${0}'.",
		send_dialog_send_more: "Изпрати ${0} файла.",
		send_dialog_sender: "Потребителско име:",
		send_dialog_password: "Парола:",
		send_dialog_missing_sender_message: "Трябва да въведете потребителско име, за да се впишете в сървъра на IBM Aspera.",
		send_dialog_missing_password_message: "Трябва да въведете парола, за да се впишете в сървъра на IBM Aspera.",
		send_dialog_title: "Изпращане чрез IBM Aspera",
		send_dialog_missing_title_message: "Трябва да въведете заглавие.",
		send_dialog_info: "Изпращане на файлове чрез сървъра на IBM Aspera и известяване на потребителите, че файловете са достъпни за изтегляне.",
		send_dialog_recipients_label: "Получатели:",
		send_dialog_recipients_textfield_hover_help: "Използвайте запетая за да отделите имейл адресите и/или потребителските имена. Например, въведете 'адрес1, адрес2, потребителско име1, потребителско име2'.",
		send_dialog_missing_recipients_message: "Трябва да посочите поне един имейл адрес или потребителско име.",
		send_dialog_title_label: "Заглавие:",
		send_dialog_note_label: "Добави съобщение",
		send_dialog_earPassphrase_label: "Парола за шифроване:",
		send_dialog_earPassphrase_textfield_hover_help: "Въведете парола за да шифровате файловете на сървъра. Впоследствие, от получателите ще се изисква да въведат паролата, за да дешифрират защитените файлове,  докато се изтеглят.",
		send_dialog_notify_title: "Уведомяване: ${0}",
		send_dialog_notifyOnUpload_label: "Уведомете ме, когато файлът е качен",
		send_dialog_notifyOnDownload_label: "Уведомете ме, когато файлът бъде изтеглен",
		send_dialog_notifyOnUploadDownload: "Уведомете ме, когато файлът бъде качен и изтеглен",
		send_dialog_send_button_label: "Изпращане",
		send_dialog_started: "Пакетът ${0} се изпраща.",
		status_started: "Статус на пакета: ${0} - Изпълнява се (${1}%)",
		status_stopped: "Статус на пакета: ${0} - Спрян",
		status_failed: "Статус на пакета: ${0} - Неуспешен",
		status_completed: "Статус на пакета: ${0} - Завършен",

		// error
		send_dialog_too_many_items_error: "Елементите не могат да бъдат изпратени.",
		send_dialog_too_many_items_error_explanation: "Можете да изпращате до ${0} елемента едновременно. Опитвате се да изпратите ${1} елемента.",
		send_dialog_too_many_items_error_userResponse: "Изберете по-малко елементи и отново се опитайте да ги изпратите. Може също да се свържете с Вашия системен администратор, за да увеличи максималния брой елементи, които може да изпращате едновременно.",
		send_dialog_too_many_items_error_0: "максимален брой елементи",
		send_dialog_too_many_items_error_1: "брой елементи",
		send_dialog_too_many_items_error_number: 5050,
});

