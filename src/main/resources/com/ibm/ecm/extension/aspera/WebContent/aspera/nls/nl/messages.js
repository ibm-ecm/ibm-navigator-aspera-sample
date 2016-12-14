/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

define({
		// NLS_CHARSET=UTF-8

		// configuration
		configuration_pane_aspera_url: "URL van IBM Aspera-server",
		configuration_pane_aspera_url_hover: "Typ de URL van de IBM Aspera-server. Bijvoorbeeld: https://hostnaam:poortnummer/aspera/faspex",
		configuration_pane_aspera_url_prompt: "U wordt dringend geadviseerd om het HTTPS-protocol te gebruiken.",
		configuration_pane_max_docs_to_send: "Maximumaantal items dat verzonden kan worden:",
		configuration_pane_max_docs_to_send_hover: "Geef het maximum aantal items aan dat per keer door gebruikers kunnen worden verzonden. ",
		configuration_pane_max_procs_to_send: "Maximale aantal gelijktijdige aanvragen",
		configuration_pane_max_procs_to_send_hover: "Geef op hoeveel aanvragen er maximaal tegelijkertijd actief kunnen zijn. ",
		configuration_pane_target_transfer_rate: "Beoogde overdrachtssnelheid (in Mbps)",
		configuration_pane_target_transfer_rate_hover: "Geef de beoogde overdrachtssnelheid op in Mbps (megabits per seconde). De snelheid wordt beperkt door de licentie. ",
		configuration_pane_speed_info: "Uw huidige licentieniveau is geldig voor 20 Mbps. Breng een upgrade aan naar een snellere evaluatielicentie (maximaal 10 Gbps) voor Aspera Faspex. Deze licentie kunt u aanvragen op de pagina <a target='_blank' href='https://ibm.biz/BdjYHq'>Aspera Evaluation Request</a>.",

		// runtime
		send_dialog_sender_title: "Afzender: ${0}",
		send_dialog_not_set: "Niet ingesteld",
		send_dialog_send_one: "'${0}' verzenden.",
		send_dialog_send_more: "${0} bestanden verzenden.",
		send_dialog_sender: "Gebruikersnaam:",
		send_dialog_password: "Wachtwoord:",
		send_dialog_missing_sender_message: "U moet uw gebruikersnaam invoeren om u aan te melden bij de IBM Aspera-server.",
		send_dialog_missing_password_message: "U moet uw wachtwoord invoeren om u aan te melden bij de IBM Aspera-server.",
		send_dialog_title: "Verzenden via IBM Aspera",
		send_dialog_missing_title_message: "U moet een titel opgeven. ",
		send_dialog_info: "Bestanden verzenden via de IBM Aspera-server en gebruikers een bericht sturen dat de bestanden beschikbaar zijn voor download.",
		send_dialog_recipients_label: "Ontvangers:",
		send_dialog_recipients_textfield_hover_help: "Gebruik komma's om e-mailadressen en/of gebruikersnamen van elkaar te scheiden. Typ bijvoorbeeld: 'adres1, adres2, gebruikersnaam1, gebruikersnaam2'.",
		send_dialog_missing_recipients_message: "U moet minimaal één e-mailadres of gebruikersnaam opgeven. ",
		send_dialog_title_label: "Titel:",
		send_dialog_note_label: "Voeg een bericht toe.",
		send_dialog_earPassphrase_label: "Versleutelingswachtwoord:",
		send_dialog_earPassphrase_textfield_hover_help: "Voer een wachtwoord in om de bestanden te versleutelen op de server. Nadien moeten de ontvangers dat wachtwoord invoeren om de beveiligde bestanden bij het downloaden te ontsleutelen. ",
		send_dialog_notify_title: "Notificatie: ${0}",
		send_dialog_notifyOnUpload_label: "Mij waarschuwen wanneer het bestand wordt geüpload",
		send_dialog_notifyOnDownload_label: "Mij waarschuwen wanneer het bestand wordt gedownload",
		send_dialog_notifyOnUploadDownload: "Mij waarschuwen wanneer het bestand wordt geüpload en gedownload",
		send_dialog_send_button_label: "Verzenden",
		send_dialog_started: "Het pakket ${0} wordt verzonden.",
		status_started: "Pakketstatus: ${0} - In uitvoering (${1}%)",
		status_stopped: "Pakketstatus: ${0} - Gestopt",
		status_failed: "Pakketstatus: ${0} - Mislukt",
		status_completed: "Pakketstatus: ${0} - Voltooid",

		// error
		send_dialog_too_many_items_error: "De items kunnen niet worden verzonden. ",
		send_dialog_too_many_items_error_explanation: "U kunt maximaal ${0} items tegelijk verzenden. U probeert echter ${1} items te verzenden.",
		send_dialog_too_many_items_error_userResponse: "Selecteer minder items en probeer nogmaals om ze te verzenden. U kunt uw systeembeheerder ook vragen om een hogere waarde in te stellen voor het maximumaantal items dat per keer kan worden verzonden.",
		send_dialog_too_many_items_error_0: "maximum_aantal_items",
		send_dialog_too_many_items_error_1: "aantal_items",
		send_dialog_too_many_items_error_number: 5050,
});

