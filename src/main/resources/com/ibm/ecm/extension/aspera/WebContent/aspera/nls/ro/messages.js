/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

define({
		// NLS_CHARSET=UTF-8

		// configuration
		configuration_pane_aspera_url: "URL server IBM Aspera",
		configuration_pane_aspera_url_hover: "Introduceţi URL-ul serverului IBM Aspera. De exemplu: https://host_name:port_number/aspera/faspex",
		configuration_pane_aspera_url_prompt: "Este foarte recomandat să utilizaţi protocolul.",
		configuration_pane_max_docs_to_send: "Numărul maxim de articole de trimis",
		configuration_pane_max_docs_to_send_hover: "Specificaţi numărul maxim de articole pe care utilizatorii le pot trimite la un moment dat.",
		configuration_pane_max_procs_to_send: "Numărul maxim de cereri concurente",
		configuration_pane_max_procs_to_send_hover: "Specificaţi numărul maxim de cereri care pot rula în acelaşi timp.",
		configuration_pane_target_transfer_rate: "Rată de transfer ţintă (în Mbps)",
		configuration_pane_target_transfer_rate_hover: "Specificaţi rata de transfer ţintă în megabiţi pe secundă. Rata este limitată de licenţă. ",
		configuration_pane_speed_info: "Licenţa dumneavoastră curentă de nivel entry este pentru 20 Mbps. Modernizaţi la o licenţă de evaluare mai rapidă (până la 10 Gbps) pentru Aspera Faspex prin cerere pe pagina <a target='_blank' href='https://ibm.biz/BdjYHq'>Aspera Evaluation Request</a>.",

		// runtime
		send_dialog_sender_title: "Expeditor: ${0}",
		send_dialog_not_set: "Nesetat",
		send_dialog_send_one: "Trimiteţi '${0}'.",
		send_dialog_send_more: "Trimiteţi ${0} fişier.",
		send_dialog_sender: "Nume utilizator:",
		send_dialog_password: "Parolă:",
		send_dialog_missing_sender_message: "Trebuie să introduceţi un nume de utilizator care să se logheze la serverul IBM Aspera.",
		send_dialog_missing_password_message: "Trebuie să introduceţi o parolă pentru logarea la serverul IBM Aspera.",
		send_dialog_title: "Trimitere via IBM Aspera",
		send_dialog_missing_title_message: "Trebuie să introduceţi un titlu.",
		send_dialog_info: "Trimiteţi fişiere via serverul IBM Aspera şi notificaţi utilizatorii că fişierele sunt disponibile pentru descărcare.",
		send_dialog_recipients_label: "Destinatari:",
		send_dialog_recipients_textfield_hover_help: "Folosiţi o virgulă pentru a separa adresele de e-mail şi/sau numele de utilizatori. De exemplu, introduceţi 'adresa1, adresa2, nume_utilizator1, nume_utilizator2'.",
		send_dialog_missing_recipients_message: "Trebuie să specificaţi cel puţin o adresă de e-mail sau un nume de utilizator.",
		send_dialog_title_label: "Titlu:",
		send_dialog_note_label: "Adăugaţi un mesaj.",
		send_dialog_earPassphrase_label: "Parolă de criptare:",
		send_dialog_earPassphrase_textfield_hover_help: "Introduceţi parolă pentru criptarea fişierele pe server. În consecinţă, destinatarilor li se va cere să introducă parola pentru a decripta fişierele când le descarcă.",
		send_dialog_notify_title: "Notificare: ${0}",
		send_dialog_notifyOnUpload_label: "Anunţaţi-mă atunci când fişierul este încărcat",
		send_dialog_notifyOnDownload_label: "Anunţaţi-mă atunci când fişierul este descărcat",
		send_dialog_notifyOnUploadDownload: "Anunţaţi-mă atunci când fişierul este încărcat şi descărcat",
		send_dialog_send_button_label: "Trimitere",
		send_dialog_started: "Pachetul ${0} este trimis.",
		status_started: "Stare pachet: ${0} - În progres (${1}%)",
		status_stopped: "Stare pachet: ${0} - Oprit",
		status_failed: "Stare pachet: ${0} - Eşuat",
		status_completed: "Stare pachet: ${0} - Finalizat",

		// error
		send_dialog_too_many_items_error: "Articolele nu pot fi trimise.",
		send_dialog_too_many_items_error_explanation: "Puteţi trimite până la ${0} articole la un moment dat. Încercaţi să trimiteţi ${1} articole.",
		send_dialog_too_many_items_error_userResponse: "Selectaţi mai puţine articole şi încercaţi din nou să le trimiteţi. De asemenea, puteţi contacta administratorul de sistem pentru a creşte numărul maxim de articole pe care le puteţi trimite la un moment dat.",
		send_dialog_too_many_items_error_0: "maximum_number_of_items",
		send_dialog_too_many_items_error_1: "number_of_items",
		send_dialog_too_many_items_error_number: 5050,
});

