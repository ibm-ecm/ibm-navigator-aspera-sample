/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

define({
		// NLS_CHARSET=UTF-8

		// configuration
		configuration_pane_aspera_url: "URL IBM Aspera poslužitelja",
		configuration_pane_aspera_url_hover: "Unesite URL IBM Aspera poslužitelja. Na primjer: https://host_name:port_number/aspera/faspex",
		configuration_pane_aspera_url_prompt: "Preporučuje se da koristite HTTPS protokol.",
		configuration_pane_max_docs_to_send: "Maksimalan broj stavki za poslati",
		configuration_pane_max_docs_to_send_hover: "Navedite maksimalan broj stavki koje korisnici mogu poslati odjednom.",
		configuration_pane_max_procs_to_send: "Maksimalni broj istodobnih zahtjeva",
		configuration_pane_max_procs_to_send_hover: "Navedite maksimalan broj zahtjeva koji se mogu istodobno izvoditi.",
		configuration_pane_target_transfer_rate: "Ciljna brzina prijenosa (u Mbps)",
		configuration_pane_target_transfer_rate_hover: "Navedite ciljnu brzinu prijenosa u megabitima po sekundi. Brzina je ograničena licencom.",
		configuration_pane_speed_info: "Vaša trenutna licenca početne razine je za 20 Mbps. Nadogradite na bržu licencu procjene (do 10 Gbps) za Aspera Faspex zatraživši je na <a target='_blank' href='https://ibm.biz/BdjYHq'>Aspera zahtjevu procjene</a> stranici.",

		// runtime
		send_dialog_sender_title: "Pošiljatelj: ${0}",
		send_dialog_not_set: "Nije postavljeno",
		send_dialog_send_one: "Pošalji '${0}'.",
		send_dialog_send_more: "Pošalji ${0} datoteka.",
		send_dialog_sender: "Ime korisnika:",
		send_dialog_password: "Lozinka:",
		send_dialog_missing_sender_message: "Morate unijeti korisničko ime da biste se prijavili na IBM Aspera poslužitelj.",
		send_dialog_missing_password_message: "Morate unijeti lozinku da biste se prijavili na IBM Aspera poslužitelj.",
		send_dialog_title: "Poslano putem IBM Aspera",
		send_dialog_missing_title_message: "Morate upisati naslov.",
		send_dialog_info: "Šaljite datoteke putem IBM Aspera poslužitelja i obavijestite korisnike da su datoteke dostupne za preuzimanje.",
		send_dialog_recipients_label: "Primatelji:",
		send_dialog_recipients_textfield_hover_help: "Koristite zarez za odvajanje e-mail adresa i/ili korisničkih imena. Na primjer, unesite 'adresa1, adresa2, korisničkoime1, korisničkoime2'.",
		send_dialog_missing_recipients_message: "Morate navesti najmanje jednu e-mail adresu ili korisničko ime.",
		send_dialog_title_label: "Naslov:",
		send_dialog_note_label: "Dodajte poruku.",
		send_dialog_earPassphrase_label: "Lozinka šifriranja:",
		send_dialog_earPassphrase_textfield_hover_help: "Unesite lozinku za šifriranje datoteka na poslužitelju. Nakon toga, primatelji će morati unijeti lozinku za dešifriranje datoteka prilikom preuzimanja.",
		send_dialog_notify_title: "Obavijest: ${0}",
		send_dialog_notifyOnUpload_label: "Obavijesti me kad je datoteka predana",
		send_dialog_notifyOnDownload_label: "Obavijesti me kad je datoteka preuzeta",
		send_dialog_notifyOnUploadDownload: "Obavijesti me kad je datoteka predana i preuzeta",
		send_dialog_send_button_label: "Pošalji",
		send_dialog_started: "Paket ${0} se šalje.",
		status_started: "Status paketa: ${0} - U tijeku (${1}%)",
		status_stopped: "Status paketa: ${0} - Zaustavljen",
		status_failed: "Status paketa: ${0} - Nije uspio",
		status_completed: "Status paketa: ${0} - Dovršen",

		// error
		send_dialog_too_many_items_error: "Stavke se ne mogu poslati.",
		send_dialog_too_many_items_error_explanation: "Možete istodobno poslati do ${0} stavke. Pokušavate poslati ${1} stavki.",
		send_dialog_too_many_items_error_userResponse: "Izaberite manje stavki pa ih pokušajte ponovno poslati. Također možete kontaktirati sistemskog administratora da poveća maksimalan broj stavki koje možete istodobno poslati.",
		send_dialog_too_many_items_error_0: "maximum_number_of_items",
		send_dialog_too_many_items_error_1: "number_of_items",
		send_dialog_too_many_items_error_number: 5050,
});

