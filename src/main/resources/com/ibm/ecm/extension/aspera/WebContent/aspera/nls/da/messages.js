/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

define({
		// NLS_CHARSET=UTF-8

		// configuration
		configuration_pane_aspera_url: "URL til IBM Aspera-server",
		configuration_pane_aspera_url_hover: "Angiv URL'en til IBM Aspera-serveren. Eksempel: https://værtsnavn:portnummer/aspera/faspex",
		configuration_pane_aspera_url_prompt: "Det kan stærkt anbefales, at du bruger HTTPS-protokollen.",
		configuration_pane_max_docs_to_send: "Maksimalt antal elementer, der skal sendes",
		configuration_pane_max_docs_to_send_hover: "Angiv det maksimale antal elementer, som brugere kan sende på én gang.",
		configuration_pane_max_procs_to_send: "Maksimalt antal samtidige anmodninger",
		configuration_pane_max_procs_to_send_hover: "Angiv det maksimale antal anmodninger, der kan udføres samtidigt.",
		configuration_pane_target_transfer_rate: "Måloverførselshastighed (i Mbps)",
		configuration_pane_target_transfer_rate_hover: "Angiv måloverførselshastigheden i megabit pr. sekund. Hastigheden begrænses af licensen.",
		configuration_pane_speed_info: "Din aktuelle licens er på 20 Mbps. Opgradér til en hurtigere evalueringslicens (op til 10 Gbps) til Aspera Faspex ved at anmode om den på siden <a target='_blank' href='https://ibm.biz/BdjYHq'>Aspera Evaluation Request</a>.",

		// runtime
		send_dialog_sender_title: "Afsender: ${0}",
		send_dialog_not_set: "Ikke angivet",
		send_dialog_send_one: "Send '${0}'.",
		send_dialog_send_more: "Send ${0} filer.",
		send_dialog_sender: "Brugernavn:",
		send_dialog_password: "Kodeord:",
		send_dialog_missing_sender_message: "Du skal angive et brugernavn for at logge på IBM Aspera-serveren.",
		send_dialog_missing_password_message: "Du skal angive et kodeord for at logge på IBM Aspera-serveren.",
		send_dialog_title: "Send via IBM Aspera",
		send_dialog_missing_title_message: "Du skal angive en titel.",
		send_dialog_info: "Send filer via IBM Aspera-serveren, og advisér brugere om, at filerne er klar til downloadning.",
		send_dialog_recipients_label: "Modtagere:",
		send_dialog_recipients_textfield_hover_help: "Brug et komma til at adskille e-mailadresser og brugernavne. Skriv f.eks. 'adresse1, adresse2,brugernavn1, brugernavn2'.",
		send_dialog_missing_recipients_message: "Du skal angive mindst én e-mailadresse eller et brugernavn.",
		send_dialog_title_label: "Titel:",
		send_dialog_note_label: "Tilføj en meddelelse.",
		send_dialog_earPassphrase_label: "Krypteringskodeord:",
		send_dialog_earPassphrase_textfield_hover_help: "Angiv et kodeord for at kryptere filerne på serveren. Derefter bliver modtagere bedt om at angive kodeordet for at dekryptere beskyttede filer, som er ved at blive downloadet.",
		send_dialog_notify_title: "Besked: ${0}",
		send_dialog_notifyOnUpload_label: "Giv mig besked, når filen er uploadet",
		send_dialog_notifyOnDownload_label: "Giv mig besked, når filen er downloadet",
		send_dialog_notifyOnUploadDownload: "Giv mig besked, når filen er uploadet og downloadet",
		send_dialog_send_button_label: "Send",
		send_dialog_started: "Pakken ${0} sendes.",
		status_started: "Pakkestatus: ${0} - i gang (${1}%)",
		status_stopped: "Pakkestatus: ${0} - stoppet",
		status_failed: "Pakkestatus: ${0} - fejl",
		status_completed: "Pakkestatus: ${0} - udført",

		// error
		send_dialog_too_many_items_error: "Elementerne kan ikke sendes.",
		send_dialog_too_many_items_error_explanation: "Du kan sende op til ${0} elementer ad gangen. Du forsøger at sende ${1} elementer.",
		send_dialog_too_many_items_error_userResponse: "Vælg færre elementer, og prøv at sende elementerne igen. Du kan også kontakte systemadministratoren for at få øget det maksimale antal elementer, du kan sende på én gang.",
		send_dialog_too_many_items_error_0: "maksimalt_antal_elementer",
		send_dialog_too_many_items_error_1: "antal_elementer",
		send_dialog_too_many_items_error_number: 5050,
});

