/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

define({
		// NLS_CHARSET=UTF-8

		// configuration
		configuration_pane_aspera_url: "URL strežnika za IBM Aspera",
		configuration_pane_aspera_url_hover: "Vnesite URL strežnika za IBM Aspera. Na primer: https://host_name:port_number/aspera/faspex",
		configuration_pane_aspera_url_prompt: "Priporočamo, da uporabite protokol HTTPS.",
		configuration_pane_max_docs_to_send: "Največje število postavk za pošiljanje",
		configuration_pane_max_docs_to_send_hover: "Podajte največje število postavk, ki jih lahko uporabniki naenkrat pošljejo.",
		configuration_pane_max_procs_to_send: "Največje število hkratnih zahtev",
		configuration_pane_max_procs_to_send_hover: "Podajte največje število zahtev, ki se lahko izvajajo istočasno.",
		configuration_pane_target_transfer_rate: "Ciljna hitrost prenosa (v Mb/s)",
		configuration_pane_target_transfer_rate_hover: "Določite ciljno hitrost prenosa v megabitih na sekundo. Hitrost je omejena glede na licenco.",
		configuration_pane_speed_info: "Vaša trenutna vstopna licenca podpira 20 Mb/s. Nadgradite jo na hitrejšo ocenjevalno licenco (do 10 Gb/s) za Aspera Faspex tako, da to zahtevate na strani <a target='_blank' href='https://ibm.biz/BdjYHq'>Prošnja za ocenitev storitve Aspera</a>.",

		// runtime
		send_dialog_sender_title: "Pošiljatelj: ${0}",
		send_dialog_not_set: "Ni nastavljeno",
		send_dialog_send_one: "Pošlji '${0}'.",
		send_dialog_send_more: "Pošlji toliko datotek: ${0}.",
		send_dialog_sender: "Uporabniško ime:",
		send_dialog_password: "Geslo:",
		send_dialog_missing_sender_message: "Vnesti morate uporabniško ime za prijavo v strežnik za IBM Aspera.",
		send_dialog_missing_password_message: "Vnesti morate geslo za prijavo v strežnik za IBM Aspera.",
		send_dialog_title: "Pošlji preko IBM Aspera",
		send_dialog_missing_title_message: "Vnesti morate naziv.",
		send_dialog_info: "Pošljite datoteke prek strežnika IBM Aspera in obvestite uporabnike, da so datoteke na voljo za prenos.",
		send_dialog_recipients_label: "Prejemniki:",
		send_dialog_recipients_textfield_hover_help: "E-poštne naslove in uporabniška imena ločite z vejico. Vnesite na primer 'naslov1, naslov2, uporabniškoime1, uporabniškoime2'.",
		send_dialog_missing_recipients_message: "Navedite vsaj en e-poštni naslov ali uporabniško ime.",
		send_dialog_title_label: "Naslov:",
		send_dialog_note_label: "Dodajte sporočilo.",
		send_dialog_earPassphrase_label: "Šifrirno geslo:",
		send_dialog_earPassphrase_textfield_hover_help: "Vnesite geslo za šifriranje datotek v strežniku. Prejemniki bodo nato morali vnesti geslo za dešifriranje zaščitenih datotek pri njihovem prenosu.",
		send_dialog_notify_title: "Obvestilo: ${0}",
		send_dialog_notifyOnUpload_label: "Obvesti me, ko se datoteka naloži",
		send_dialog_notifyOnDownload_label: "Obvesti me, ko se datoteka prenese",
		send_dialog_notifyOnUploadDownload: "Obvesti me, ko se datoteka naloži in prenese",
		send_dialog_send_button_label: "Pošlji",
		send_dialog_started: "Pošiljanje paketa ${0} je v teku.",
		status_started: "Status paketa: ${0} - v teku (${1} %)",
		status_stopped: "Status paketa: ${0} - ustavljeno",
		status_failed: "Status paketa: ${0} - ni uspelo",
		status_completed: "Status paketa: ${0} - dokončano",

		// error
		send_dialog_too_many_items_error: "Postavk ni mogoče poslati.",
		send_dialog_too_many_items_error_explanation: "Hkrati lahko pošljete največ ${0} postavk, Poskušate poslati ${1} postavk.",
		send_dialog_too_many_items_error_userResponse: "Izberite manjše število postavk in jih znova poskusite poslati. Če želite, se lahko obrnete na skrbnika sistema, ki bo povečal največje število postavk, ki jih je mogoče hkrati poslati.",
		send_dialog_too_many_items_error_0: "maximum_number_of_items",
		send_dialog_too_many_items_error_1: "number_of_items",
		send_dialog_too_many_items_error_number: 5050,
});

