/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

define({
		// NLS_CHARSET=UTF-8

		// configuration
		configuration_pane_aspera_url: "URL del servidor de IBM Aspera",
		configuration_pane_aspera_url_hover: "Escriba el URL del servidor de IBM Aspera. Por ejemplo: https://nombre_host:número_puerto/aspera/faspex",
		configuration_pane_aspera_url_prompt: "Se recomienda utilizar el protocolo HTTPS.",
		configuration_pane_max_docs_to_send: "Número máximo de elementos para enviar",
		configuration_pane_max_docs_to_send_hover: "Especifique el número máximo de elementos que los usuarios pueden enviar cada vez.",
		configuration_pane_max_procs_to_send: "Número máximo de solicitudes simultáneas",
		configuration_pane_max_procs_to_send_hover: "Especifique el número máximo de solicitudes que se pueden ejecutar cada vez.",
		configuration_pane_target_transfer_rate: "Velocidad de transferencia de destino (en Mbps)",
		configuration_pane_target_transfer_rate_hover: "Especifique la velocidad de transferencia de destino en megabits por segundo. La velocidad está limitada por la licencia.",
		configuration_pane_speed_info: "Su licencia de nivel de entrada actual es para 20 Mbps. Puede actualizarse a una licencia de evaluación más rápida (hasta 10 Gbps) para Aspera Faspex solicitándolo en la página <a target='_blank' href='https://ibm.biz/BdjYHq'>Aspera Evaluation Request</a>.",

		// runtime
		send_dialog_sender_title: "Remitente: ${0}",
		send_dialog_not_set: "No establecido",
		send_dialog_send_one: "Enviar '${0}'.",
		send_dialog_send_more: "Enviar ${0} archivos.",
		send_dialog_sender: "Nombre de usuario:",
		send_dialog_password: "Contraseña:",
		send_dialog_missing_sender_message: "Debe introducir un nombre de usuario para iniciar sesión en el servidor de IBM Aspera.",
		send_dialog_missing_password_message: "Debe introducir una contraseña para iniciar sesión en el servidor de IBM Aspera.",
		send_dialog_title: "Enviar a través de IBM Aspera",
		send_dialog_missing_title_message: "Debe introducir un título.",
		send_dialog_info: "Envíe archivos a través del servidor de IBM Aspera y notifique a los usuarios que los archivos están disponibles para su descarga.",
		send_dialog_recipients_label: "Destinatarios:",
		send_dialog_recipients_textfield_hover_help: "Utilice una coma para separar direcciones de correo electrónico y/o nombres de usuario. Por ejemplo, escriba 'address1, address2, username1, username2'.",
		send_dialog_missing_recipients_message: "Debe especificar al menos una dirección de correo electrónico o un nombre de usuario.",
		send_dialog_title_label: "Título:",
		send_dialog_note_label: "Añada un mensaje.",
		send_dialog_earPassphrase_label: "Contraseña de cifrado:",
		send_dialog_earPassphrase_textfield_hover_help: "Introduzca una contraseña para cifrar los archivos en el servidor. De este modo, los destinatarios deberán introducir la contraseña para descifrar los archivos protegidos al descargarlos.",
		send_dialog_notify_title: "Notificación: ${0}",
		send_dialog_notifyOnUpload_label: "Notificarme cuando se cargue el archivo",
		send_dialog_notifyOnDownload_label: "Notificarme cuando se descargue el archivo",
		send_dialog_notifyOnUploadDownload: "Notificarme cuando se cargue y descargue el archivo",
		send_dialog_send_button_label: "Enviar",
		send_dialog_started: "El paquete ${0} se está enviando.",
		status_started: "Estado del paquete: ${0} - En curso (${1}%)",
		status_stopped: "Estado del paquete: ${0} - Detenido",
		status_failed: "Estado del paquete: ${0} - Fallido",
		status_completed: "Estado del paquete: ${0} - Completado",

		// error
		send_dialog_too_many_items_error: "No se pueden enviar los elementos.",
		send_dialog_too_many_items_error_explanation: "Puede enviar hasta ${0} elementos cada vez. Está intentando enviar ${1} elementos.",
		send_dialog_too_many_items_error_userResponse: "Seleccione menos elementos e intente volver a enviar los elementos. También puede ponerse en contacto con los administradores del sistema para aumentar el número máximo de elementos que se pueden enviar cada vez.",
		send_dialog_too_many_items_error_0: "número_máximo_de_elementos",
		send_dialog_too_many_items_error_1: "número_de_elementos",
		send_dialog_too_many_items_error_number: 5050,
});

