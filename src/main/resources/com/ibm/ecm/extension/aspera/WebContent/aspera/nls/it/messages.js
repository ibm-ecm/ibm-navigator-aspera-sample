/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

define({
		// NLS_CHARSET=UTF-8

		// configuration
		configuration_pane_aspera_url: "URL server IBM Aspera",
		configuration_pane_aspera_url_hover: "Immettere l'URL del server IBM Aspera. Ad esempio: https://host_name:port_number/aspera/faspex",
		configuration_pane_aspera_url_prompt: "Si consiglia di utilizzare il protocollo HTTPS.",
		configuration_pane_max_docs_to_send: "Numero massimo di elementi da inviare",
		configuration_pane_max_docs_to_send_hover: "Specificare il numero di elementi che gli utenti possono inviare ogni volta.",
		configuration_pane_max_procs_to_send: "Numero massimo di richieste simultanee",
		configuration_pane_max_procs_to_send_hover: "Specificare il numero massimo di richieste che possono essere in esecuzione contemporaneamente.",
		configuration_pane_target_transfer_rate: "Velocità di trasferimento di destinazione (in Mbps)",
		configuration_pane_target_transfer_rate_hover: "Specifica la velocità di trasferimento di destinazione in megabit per secondo. La velocità è limitata dalla licenza. ",
		configuration_pane_speed_info: "La licenza attuale è per 20 Mbps. Per effettuare l'aggiornamento ad una licenza di prova più veloce (fino a 10 Gbps) per Aspera Faspex richiedendola nella pagina <a target='_blank' href='https://ibm.biz/BdjYHq'>Aspera Evaluation Request</a>.",

		// runtime
		send_dialog_sender_title: "Mittente: ${0}",
		send_dialog_not_set: "Non impostato",
		send_dialog_send_one: "Invia '${0}'.",
		send_dialog_send_more: "Invia ${0} file.",
		send_dialog_sender: "Nome utente:",
		send_dialog_password: "Password:",
		send_dialog_missing_sender_message: "È necessario immettere un nome utente per accedere al server IBM Aspera.",
		send_dialog_missing_password_message: "È necessario immettere una password per accedere al server IBM Aspera.",
		send_dialog_title: "Invia tramite IBM Aspera",
		send_dialog_missing_title_message: "È necessario immettere un titolo.",
		send_dialog_info: "Inviare i file tramite il server IBM Aspera ed informare gli utenti che i file sono disponibili per il download.",
		send_dialog_recipients_label: "Destinatari:",
		send_dialog_recipients_textfield_hover_help: "Utilizzare una virgola per separare gli indirizzi e-mail o i nomi utente. Ad esempio, immettere 'indirizzo1, indirizzo2, nomeutente1, nomeutente2'.",
		send_dialog_missing_recipients_message: "È necessario specificare almeno un indirizzo e-mail o nome utente.",
		send_dialog_title_label: "Titolo:",
		send_dialog_note_label: "Aggiungere un messaggio.",
		send_dialog_earPassphrase_label: "Password di crittografia:",
		send_dialog_earPassphrase_textfield_hover_help: "Immettere una password per crittografare i file sul server. Successivamente, i destinatari dovranno immettere la password per decodificare i file protetti una volta scaricati.",
		send_dialog_notify_title: "Notifica: ${0}",
		send_dialog_notifyOnUpload_label: "Informa quando il file è stato caricato.",
		send_dialog_notifyOnDownload_label: "Informa quando il file è stato scaricato.",
		send_dialog_notifyOnUploadDownload: "Informa quando il file è stato caricato e scaricato.",
		send_dialog_send_button_label: "Invia",
		send_dialog_started: "Invio del package ${0} in corso.",
		status_started: "Stato package: ${0} - In corso (${1}%)",
		status_stopped: "Stato package: ${0} - Arrestato",
		status_failed: "Stato package: ${0} - Non riuscito",
		status_completed: "Stato package: ${0} - Completato",

		// error
		send_dialog_too_many_items_error: "Impossibile inviare gli elementi.",
		send_dialog_too_many_items_error_explanation: "È possibile inviare fino a ${0} elementi per volta. Si sta tentando di aggiungere ${1} elementi.",
		send_dialog_too_many_items_error_userResponse: "Selezionare meno elementi e tentare di inviare di nuovo gli elementi. È inoltre possibile rivolgersi all'amministratore di sistema per incrementare il numero massimo di elementi che è possibile inviare contemporaneamente.",
		send_dialog_too_many_items_error_0: "numero_massimo_di_elementi",
		send_dialog_too_many_items_error_1: "numero_di_elementi",
		send_dialog_too_many_items_error_number: 5050,
});

