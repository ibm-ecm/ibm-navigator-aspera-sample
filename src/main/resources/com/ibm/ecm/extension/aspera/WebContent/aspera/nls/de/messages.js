/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

define({
		// NLS_CHARSET=UTF-8

		// configuration
		configuration_pane_aspera_url: "IBM Aspera-Server-URL",
		configuration_pane_aspera_url_hover: "Geben Sie die URL des IBM Aspera-Servers ein. Beispiel: https://host_name:port_number/aspera/faspex",
		configuration_pane_aspera_url_prompt: "Es wird dringend empfohlen, das HTTPS-Protokoll zu verwenden.",
		configuration_pane_max_docs_to_send: "Maximale Anzahl zu sendender Elemente:",
		configuration_pane_max_docs_to_send_hover: "Geben Sie die maximale Anzahl Elemente an, die Benutzer gleichzeitig senden können.",
		configuration_pane_max_procs_to_send: "Maximale Anzahl gleichzeitiger Anforderungen",
		configuration_pane_max_procs_to_send_hover: "Geben Sie die maximale Anzahl Anforderungen an, die gleichzeitig ausgeführt werden können.",
		configuration_pane_target_transfer_rate: "Zielübertragungsrate (in MB/s)",
		configuration_pane_target_transfer_rate_hover: "Geben Sie die Zielübertragungsrate in Mb/s an. Die Rate wird durch die Lizenz begrenzt. ",
		configuration_pane_speed_info: "Ihre aktuelle Einstiegslizenz gilt für 20 MB/s. Führen Sie ein Upgrade auf eine schnellere Testlizenz (bis zu 100 Gb/s) für Aspera Faspex durch, indem auf der Seite für <a target='_blank' href='https://ibm.biz/BdjYHq'>Aspera-Testanforderungen</a> eine entsprechende Anforderung absetzen.",

		// runtime
		send_dialog_sender_title: "Sender: ${0}",
		send_dialog_not_set: "Nicht festgelegt",
		send_dialog_send_one: "'${0}' senden.",
		send_dialog_send_more: "${0} Dateien senden.",
		send_dialog_sender: "Benutzername:",
		send_dialog_password: "Kennwort:",
		send_dialog_missing_sender_message: "Sie müssen einen Benutzernamen für die Anmeldung am IBM Aspera-Server eingeben.",
		send_dialog_missing_password_message: "Sie müssen ein Kennwort für die Anmeldung am IBM Aspera-Server eingeben.",
		send_dialog_title: "Über IBM Aspera senden",
		send_dialog_missing_title_message: "Sie müssen einen Titel eingeben.",
		send_dialog_info: "Senden Sie Dateien über den IBM Aspera-Server und benachrichtigen Sie die Benutzer darüber, dass die Dateien für den Download verfügbar sind.",
		send_dialog_recipients_label: "Empfänger:",
		send_dialog_recipients_textfield_hover_help: "Trennen Sie E-Mail-Adressen und/oder Benutzernamen durch Kommas. Geben Sie zum Beispiel 'Adresse1, Adresse2, Benutzername1, Benutzername2' ein.",
		send_dialog_missing_recipients_message: "Sie müssen mindestens eine E-Mail-Adresse oder einen Benutzernamen angeben.",
		send_dialog_title_label: "Titel:",
		send_dialog_note_label: "Nachricht hinzufügen.",
		send_dialog_earPassphrase_label: "Verschlüsselungskennwort:",
		send_dialog_earPassphrase_textfield_hover_help: "Geben Sie ein Kennwort zum Verschlüsseln der Dateien auf dem Server ein. Die Empfänger müssen dann das Kennwort eingeben, damit geschützte Dateien beim Download entschlüsselt werden.",
		send_dialog_notify_title: "Benachrichtigung: ${0}",
		send_dialog_notifyOnUpload_label: "Ich möchte benachrichtigt werden, wenn die Datei hochgeladen ist.",
		send_dialog_notifyOnDownload_label: "Ich möchte benachrichtigt werden, wenn die Datei heruntergeladen ist.",
		send_dialog_notifyOnUploadDownload: "Ich möchte benachrichtigt werden, wenn die Datei hoch- oder heruntergeladen ist.",
		send_dialog_send_button_label: "Senden",
		send_dialog_started: "Das Paket ${0} wird gesendet.",
		status_started: "Paketstatus: ${0} - In Bearbeitung (${1}%)",
		status_stopped: "Paketstatus: ${0} - Gestoppt",
		status_failed: "Paketstatus: ${0} - Fehlgeschlagen",
		status_completed: "Paketstatus: ${0} - Abgeschlossen",

		// error
		send_dialog_too_many_items_error: "Die Elemente können nicht gesendet werden.",
		send_dialog_too_many_items_error_explanation: "Sie können bis zu ${0} Elemente gleichzeitig senden. Sie versuchen, ${1} Elemente zu senden.",
		send_dialog_too_many_items_error_userResponse: "Wählen Sie weniger Elemente aus und versuchen Sie erneut, die Elemente zu senden. Sie können auch den Systemadministrator bitten, die maximale Anzahl der Elemente zu erhöhen, die gleichzeitig gesendet werden können.",
		send_dialog_too_many_items_error_0: "Maximale_Anzahl_Elemente",
		send_dialog_too_many_items_error_1: "Anzahl_Elemente",
		send_dialog_too_many_items_error_number: 5050,
});

