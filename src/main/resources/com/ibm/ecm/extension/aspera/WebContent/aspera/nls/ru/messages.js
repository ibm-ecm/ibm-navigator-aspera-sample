/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

define({
		// NLS_CHARSET=UTF-8

		// configuration
		configuration_pane_aspera_url: "URL сервера IBM Aspera",
		configuration_pane_aspera_url_hover: "Введите URL сервера IBM Aspera. Например: https://имя_хоста:номер_порта/aspera/faspex",
		configuration_pane_aspera_url_prompt: "Настоятельно рекомендуется использовать протокол HTTPS.",
		configuration_pane_max_docs_to_send: "Максимальное число элементов для отправки",
		configuration_pane_max_docs_to_send_hover: "Укажите максимальное число элементов, которые пользователи могут одновременно отправить.",
		configuration_pane_max_procs_to_send: "Максимальное число одновременных требований",
		configuration_pane_max_procs_to_send_hover: "Укажите максимальное число требований, которые могут выполняться одновременно.",
		configuration_pane_target_transfer_rate: "Скорость передачи назначения (в Мбит/сек.)",
		configuration_pane_target_transfer_rate_hover: "Укажите скорость передачи назначения в Мбит/сек. Скорость ограничена лицензией. ",
		configuration_pane_speed_info: "Ваша текущая лицензия начального уровня предназначена для 20 Мбит/сек. Произведите обновление до более быстрой оценочной лицензии (до 10 Гбит/сек.) для Aspera Faspex, запросив ее на странице <a target='_blank' href='https://ibm.biz/BdjYHq'>Страница оценки Aspera</a>.",

		// runtime
		send_dialog_sender_title: "Отправитель: ${0}",
		send_dialog_not_set: "Не задано",
		send_dialog_send_one: "Отправить '${0}'.",
		send_dialog_send_more: "Отправить файлы в количестве ${0}.",
		send_dialog_sender: "Имя пользователя:",
		send_dialog_password: "Пароль:",
		send_dialog_missing_sender_message: "Чтобы войти в систему на сервере IBM Aspera, нужно ввести имя пользователя.",
		send_dialog_missing_password_message: "Чтобы войти в систему на сервере IBM Aspera, нужно ввести пароль.",
		send_dialog_title: "Отправить через IBM Aspera",
		send_dialog_missing_title_message: "Вы должны ввести заголовок.",
		send_dialog_info: "Отправить файлы через сервер IBM Aspera и уведомить пользователей о том, что файлы доступны для скачивания.",
		send_dialog_recipients_label: "Получатели:",
		send_dialog_recipients_textfield_hover_help: "Чтобы разделить адреса электроннной почты и/или имена пользователей, используйте запятые. Например, введите 'address1, address2, username1, username2'.",
		send_dialog_missing_recipients_message: "Вы должны указать хотя бы один адрес электронной почты или имя пользователя.",
		send_dialog_title_label: "Заголовок:",
		send_dialog_note_label: "Добавить сообщение.",
		send_dialog_earPassphrase_label: "Пароль шифрования:",
		send_dialog_earPassphrase_textfield_hover_help: "Введите пароль, чтобы зашифровать файлы на сервере. После этого получатели должны будут вводить пароль, чтобы расшифровать защищенные файлы при их скачивании.",
		send_dialog_notify_title: "Уведомление: ${0}",
		send_dialog_notifyOnUpload_label: "Уведомить меня, когда будет закачан файл",
		send_dialog_notifyOnDownload_label: "Уведомить меня, когда будет скачан файл",
		send_dialog_notifyOnUploadDownload: "Уведомить меня, когда будет закачан и скачан файл",
		send_dialog_send_button_label: "Отправить",
		send_dialog_started: "Пакет ${0} отправляется.",
		status_started: "Состояние пакета: ${0} - Выполняется (${1}%)",
		status_stopped: "Состояние пакета: ${0} - Остановлен",
		status_failed: "Состояние пакета: ${0} - Неудачно",
		status_completed: "Состояние пакета: ${0} - Выполнен",

		// error
		send_dialog_too_many_items_error: "Не удается отправить элементы.",
		send_dialog_too_many_items_error_explanation: "Можно одновременно отправить элементы в количестве до ${0}. Вы пытаетесь отправить элементы в количестве ${1}.",
		send_dialog_too_many_items_error_userResponse: "Выберите меньшее число элементов и попробуйте снова их отправить. Также можно обратиться к системному администратору, чтобы увеличить максимальное число элементов, которые можно отправить одновременно.",
		send_dialog_too_many_items_error_0: "maximum_number_of_items",
		send_dialog_too_many_items_error_1: "number_of_items",
		send_dialog_too_many_items_error_number: 5050,
});

