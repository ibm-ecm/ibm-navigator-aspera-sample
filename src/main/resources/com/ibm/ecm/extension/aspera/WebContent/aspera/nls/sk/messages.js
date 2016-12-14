/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

define({
		// NLS_CHARSET=UTF-8

		// configuration
		configuration_pane_aspera_url: "Adresa URL servera IBM Aspera",
		configuration_pane_aspera_url_hover: "Zadajte adresu URL servera IBM Aspera. Napríklad: https://názov_hostiteľa:číslo_portu/aspera/faspex",
		configuration_pane_aspera_url_prompt: "Dôrazne odporúčame, aby ste používali protokol HTTPS.",
		configuration_pane_max_docs_to_send: "Maximálny počet odoslaných položiek",
		configuration_pane_max_docs_to_send_hover: "Zadajte maximálny počet položiek, ktoré môžu užívatelia naraz odoslať.",
		configuration_pane_max_procs_to_send: "Maximálny počet súbežných požiadaviek",
		configuration_pane_max_procs_to_send_hover: "Zadajte maximálny počet požiadaviek, ktoré môžu byť súčasne spustené.",
		configuration_pane_target_transfer_rate: "Cieľová prenosová rýchlosť (v Mb/s)",
		configuration_pane_target_transfer_rate_hover: "Zadajte cieľovú prenosovú rýchlosť ako Mb/s. Maximálna rýchlosť je obmedzená licenčnými oprávneniami.",
		configuration_pane_speed_info: "Vaša aktuálna základná licencia umožňuje maximálnu prenosovú rýchlosť 20 Mb/s. Ak chcete vykonať inováciu na rýchlejšiu skúšobnú licenciu (podporujúcu prenosovú rýchlosť až do 10 Gb/s) pre softvér Aspera Faspex, požiadajte o novú licenciu na stránke <a target='_blank' href='https://ibm.biz/BdjYHq'>Aspera Evaluation Request</a>.",

		// runtime
		send_dialog_sender_title: "Odosielateľ: ${0}",
		send_dialog_not_set: "Nenastavené",
		send_dialog_send_one: "Poslať '${0}'.",
		send_dialog_send_more: "Poslať súbory: ${0}.",
		send_dialog_sender: "Meno užívateľa:",
		send_dialog_password: "Heslo:",
		send_dialog_missing_sender_message: "Je potrebné zadať meno užívateľa pre prihlásenie na server IBM Aspera.",
		send_dialog_missing_password_message: "Je potrebné zadať heslo pre prihlásenie na server IBM Aspera.",
		send_dialog_title: "Poslať cez IBM Aspera",
		send_dialog_missing_title_message: "Musíte zadať názov.",
		send_dialog_info: "Posielajte súbory cez server IBM Aspera a upozornite užívateľov, že si môžu stiahnuť súbory.",
		send_dialog_recipients_label: "Príjemcovia:",
		send_dialog_recipients_textfield_hover_help: "E-mailové adresy alebo mená užívateľov oddeľte čiarkou. Napríklad, zadajte: 'adresa1, adresa2, meno_užívateľa1, meno_užívateľa2'.",
		send_dialog_missing_recipients_message: "Zadajte aspoň jednu e-mailovú adresu alebo meno užívateľa.",
		send_dialog_title_label: "Názov:",
		send_dialog_note_label: "Pridajte správu.",
		send_dialog_earPassphrase_label: "Heslo pre šifrovanie:",
		send_dialog_earPassphrase_textfield_hover_help: "Zadajte heslo pre zašifrovanie súborov na serveri. Toto heslo budú musieť príjemcovia zadať, aby mohli dešifrovať chránené súbory po stiahnutí.",
		send_dialog_notify_title: "Upozornenie: ${0}",
		send_dialog_notifyOnUpload_label: "Upozorniť ma na nahratie súboru",
		send_dialog_notifyOnDownload_label: "Upozorniť ma na stiahnutie súboru",
		send_dialog_notifyOnUploadDownload: "Upozorniť ma na nahratie a stiahnutie súboru",
		send_dialog_send_button_label: "Odoslať",
		send_dialog_started: "Balík ${0} sa posiela.",
		status_started: "Stav balíka: ${0} - prebieha (${1} %)",
		status_stopped: "Stav balíka: ${0} - zastavené",
		status_failed: "Stav balíka: ${0} - neúspešné",
		status_completed: "Stav balíka: ${0} - dokončené",

		// error
		send_dialog_too_many_items_error: "Nepodarilo sa odoslať položky.",
		send_dialog_too_many_items_error_explanation: "Môžete odoslať najviac ${0} položiek naraz. Pokúšate sa odoslať ${1} položiek.",
		send_dialog_too_many_items_error_userResponse: "Vyberte menší počet položiek a znova sa ich pokúste odoslať. Taktiež sa môžete obrátiť na správcu systému a požiadať ho, aby zvýšil maximálny počet súbežne odosielaných položiek.",
		send_dialog_too_many_items_error_0: "maximum_number_of_items",
		send_dialog_too_many_items_error_1: "number_of_items",
		send_dialog_too_many_items_error_number: 5050,
});

