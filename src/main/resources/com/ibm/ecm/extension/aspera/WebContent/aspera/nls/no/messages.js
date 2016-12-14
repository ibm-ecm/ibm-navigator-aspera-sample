/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

define({
		// NLS_CHARSET=UTF-8

		// configuration
		configuration_pane_aspera_url: "URL for IBM Aspera-server",
		configuration_pane_aspera_url_hover: "Oppgi URLen for IBM Aspera-serveren. For eksempel: https://vertsnavn:portnummer/aspera/faspex",
		configuration_pane_aspera_url_prompt: "Det anbefales på det sterkeste at du bruker HTTPS-protokollen.",
		configuration_pane_max_docs_to_send: "Største antall elementer som kan sendes",
		configuration_pane_max_docs_to_send_hover: "Oppgi det største antall elementer brukerne kan sende om gangen.",
		configuration_pane_max_procs_to_send: "Maksimalt antall samtidige forespørsler",
		configuration_pane_max_procs_to_send_hover: "Oppgi det maksimale antall forespørsler som kan kjøres samtidig.",
		configuration_pane_target_transfer_rate: "Måloverføringshastighet (i Mbps)",
		configuration_pane_target_transfer_rate_hover: "Oppgi måloverføringshastigheten i megabiter per sekund. Hastigheten er begrenset av lisensen.",
		configuration_pane_speed_info: "Din gjeldende lisens på innstegsnivå er for 20 Mbps. Oppgrader til en raskere evalueringslisens (opptil 10 Gbps) for Aspera Faspex ved å be om det på siden <a target='_blank' href='https://ibm.biz/BdjYHq'>Aspera Evaluation Request</a>.",

		// runtime
		send_dialog_sender_title: "Avsender: ${0}",
		send_dialog_not_set: "Ikke angitt",
		send_dialog_send_one: "Send '${0}'.",
		send_dialog_send_more: "Send ${0} filer.",
		send_dialog_sender: "Brukernavn:",
		send_dialog_password: "Passord:",
		send_dialog_missing_sender_message: "Du må oppgi et brukernavn for å logge deg på IBM Aspera-serveren.",
		send_dialog_missing_password_message: "Du må oppgi et passord for å logge deg på IBM Aspera-serveren.",
		send_dialog_title: "Send via IBM Aspera",
		send_dialog_missing_title_message: "Du må skrive inn en tittel.",
		send_dialog_info: "Send filer via IBM Aspera-serveren og varsle brukere om at filene er tilgjengelige for nedlasting.",
		send_dialog_recipients_label: "Mottakere:",
		send_dialog_recipients_textfield_hover_help: "Skill e-postadressene og/eller brukernavnene med komma. Skriv for eksempel 'adresse1, adresse2, brukernavn1, brukernavn2'.",
		send_dialog_missing_recipients_message: "Du må oppgi minst en e-postadresse eller ett brukernavn.",
		send_dialog_title_label: "Tittel:",
		send_dialog_note_label: "Legg til en melding.",
		send_dialog_earPassphrase_label: "Krypteringspassord:",
		send_dialog_earPassphrase_textfield_hover_help: "Oppgi et passord for å kryptere filene på serveren. Da må mottakere oppgi passordet for å dekryptere beskyttede filer etter hvert som de blir lastet ned.",
		send_dialog_notify_title: "Varsel: ${0}",
		send_dialog_notifyOnUpload_label: "Varsle meg når filen blir lastet opp",
		send_dialog_notifyOnDownload_label: "Varsle meg når filen blir lastet ned",
		send_dialog_notifyOnUploadDownload: "Varsle meg når filen blir lastet opp og ned",
		send_dialog_send_button_label: "Send",
		send_dialog_started: "Pakken ${0} blir sendt.",
		status_started: "Pakkestatus: ${0} - Pågår (${1}%)",
		status_stopped: "Pakkestatus: ${0} - Stoppet",
		status_failed: "Pakkestatus: ${0} - Mislykket",
		status_completed: "Pakkestatus: ${0} - Fullført",

		// error
		send_dialog_too_many_items_error: "Elementene kan ikke sendes.",
		send_dialog_too_many_items_error_explanation: "Du kan sende opptil ${0} elementer om gangen. Du prøver å sende ${1} elementer.",
		send_dialog_too_many_items_error_userResponse: "Velg færre elementer og prøv å sende elementene igjen. Du kan også kontakte systemadministratoren for å øke det maksimale antall elementer du kan sende på en gang.",
		send_dialog_too_many_items_error_0: "største_antall_elementer",
		send_dialog_too_many_items_error_1: "antall_elementer",
		send_dialog_too_many_items_error_number: 5050,
});

