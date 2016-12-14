/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

define({
		// NLS_CHARSET=UTF-8

		// configuration
		configuration_pane_aspera_url: "URL del servidor de l'IBM Aspera",
		configuration_pane_aspera_url_hover: "Introduïu l'URL del servidor de l'IBM Aspera. Per exemple: https://nom_amfitrió:número_port/aspera/faspex",
		configuration_pane_aspera_url_prompt: "Es recomana encaridament que s'utilitzi el protocol HTTPS.",
		configuration_pane_max_docs_to_send: "Nombre màxim d'elements que es poden enviar",
		configuration_pane_max_docs_to_send_hover: "Especifiqueu el nombre màxim d'elements que els usuaris poden enviar alhora.",
		configuration_pane_max_procs_to_send: "Nombre màxim de sol·licituds simultànies",
		configuration_pane_max_procs_to_send_hover: "Especifiqueu el nombre màxim de sol·licituds que es poden executar alhora.",
		configuration_pane_target_transfer_rate: "Velocitat de transferència de destinació (en Mbps)",
		configuration_pane_target_transfer_rate_hover: "Especifiqueu la velocitat de transferència de destinació en megabits per segon. La velocitat està limitada per la llicència.",
		configuration_pane_speed_info: "La vostra llicència de nivell d'entrada actual és per a 20 Mbps. Podeu actualitzar-vos a una llicència d'avaluació més ràpida (fins a 10 Gbps) per a Aspera Faspex sol·licitant-ho a la pàgina <a target='_blank' href='https://ibm.biz/BdjYHq'>Aspera Evaluation Request</a>.",

		// runtime
		send_dialog_sender_title: "Remitent: ${0}",
		send_dialog_not_set: "Sense establir",
		send_dialog_send_one: "Envia '${0}'.",
		send_dialog_send_more: "Envia ${0} fitxers.",
		send_dialog_sender: "Nom d'usuari:",
		send_dialog_password: "Contrasenya:",
		send_dialog_missing_sender_message: "Introduïu un nom d'usuari per iniciar sessió al servidor de l'IBM Aspera.",
		send_dialog_missing_password_message: "Introduïu una contrasenya per iniciar sessió al servidor de l'IBM Aspera.",
		send_dialog_title: "Envia mitjançant l'IBM Aspera",
		send_dialog_missing_title_message: "Heu d'introduir un títol.",
		send_dialog_info: "Envieu fitxers mitjançant el servidor de l'IBM Aspera i notifiqueu als usuaris que els fitxers estan disponibles per baixar-los.",
		send_dialog_recipients_label: "Destinataris:",
		send_dialog_recipients_textfield_hover_help: "Utilitzeu comes per separar adreces de correu electrònic i/o noms d'usuari. Per exemple, introduïu 'address1, address2, username1, username2'.",
		send_dialog_missing_recipients_message: "Heu d'especificar com a mínim una adreça de correu electrònic o un nom d'usuari.",
		send_dialog_title_label: "Títol:",
		send_dialog_note_label: "Afegeix un missatge.",
		send_dialog_earPassphrase_label: "Contrasenya de xifratge:",
		send_dialog_earPassphrase_textfield_hover_help: "Introduïu una contrasenya per xifrar els fitxers al servidor. Així, els destinataris hauran d'introduir la contrasenya per desxifrar els fitxers protegits quan els baixin.",
		send_dialog_notify_title: "Notificació: ${0}",
		send_dialog_notifyOnUpload_label: "Avisa'm quan el fitxer s'hagi carregat",
		send_dialog_notifyOnDownload_label: "Avisa'm quan el fitxer s'hagi baixat",
		send_dialog_notifyOnUploadDownload: "Avisa'm quan el fitxer s'hagi carregat i baixat",
		send_dialog_send_button_label: "Envia",
		send_dialog_started: "El paquet ${0} s'està enviant.",
		status_started: "Estat del paquet: ${0} - En curs (${1}%)",
		status_stopped: "Estat del paquet: ${0} - Aturat",
		status_failed: "Estat del paquet: ${0} - Fallit",
		status_completed: "Estat del paquet: ${0} - Completat",

		// error
		send_dialog_too_many_items_error: "Els elements no es poden enviar.",
		send_dialog_too_many_items_error_explanation: "Podeu enviar fins a ${0} elements alhora. Esteu intentant enviar ${1} elements.",
		send_dialog_too_many_items_error_userResponse: "Seleccioneu menys elements i intenteu tornar-los a enviar. També us podeu posar en contacte amb l'administrador del sistema per tal que augmenti el nombre màxim d'elements que podeu enviar alhora.",
		send_dialog_too_many_items_error_0: "maximum_number_of_items",
		send_dialog_too_many_items_error_1: "number_of_items",
		send_dialog_too_many_items_error_number: 5050,
});

