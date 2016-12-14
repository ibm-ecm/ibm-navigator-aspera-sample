/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

define({
		// NLS_CHARSET=UTF-8

		// configuration
		configuration_pane_aspera_url: "IBM Aspera -palvelimen URL-osoite",
		configuration_pane_aspera_url_hover: "Anna IBM Aspera -palvelimen URL-osoite. Esimerkki: https://pääkoneen_nimi:portin_numero/aspera/faspex",
		configuration_pane_aspera_url_prompt: "On erittäin suositeltavaa käyttää HTTPS-yhteyskäytäntöä.",
		configuration_pane_max_docs_to_send: "Lähetettävien objektien enimmäismäärä",
		configuration_pane_max_docs_to_send_hover: "Määritä samanaikaisesti lähetettävien objektien enimmäismäärä.",
		configuration_pane_max_procs_to_send: "Samanaikaisten pyyntöjen enimmäismäärä",
		configuration_pane_max_procs_to_send_hover: "Määritä samanaikaisesti ajossa olevien pyyntöjen enimmäismäärä.",
		configuration_pane_target_transfer_rate: "Siirron tavoitenopeus (megabitteinä sekunnissa)",
		configuration_pane_target_transfer_rate_hover: "Määritä siirron tavoitenopeus megabitteinä sekunnissa (Mbps). Nopeus on rajoitettu lisenssillä.",
		configuration_pane_speed_info: "Nykyisen peruslisenssin sisältämä nopeus on 20 Mb/s. Voit päivittää Aspera Faspex -palvelun nopeampaan arviointilisenssiin (nopeus enintään 10 Gb/s) pyytämällä sitä <a target='_blank' href='https://ibm.biz/BdjYHq'>Aspera Evaluation Request</a> -sivulla.",

		// runtime
		send_dialog_sender_title: "Lähettäjä: ${0}",
		send_dialog_not_set: "Ei määritetty",
		send_dialog_send_one: "Lähetä ${0}.",
		send_dialog_send_more: "Lähetä ${0} tiedostoa.",
		send_dialog_sender: "Käyttäjätunnus:",
		send_dialog_password: "Salasana:",
		send_dialog_missing_sender_message: "IBM Aspera -palvelimeen kirjautuminen edellyttää käyttäjätunnusta.",
		send_dialog_missing_password_message: "IBM Aspera -palvelimeen kirjautuminen edellyttää salasanaa.",
		send_dialog_title: "Lähetä IBM Aspera -palvelun kautta",
		send_dialog_missing_title_message: "Otsikko on annettava.",
		send_dialog_info: "Lähetä tiedostot IBM Aspera -palvelimen kautta ja ilmoita käyttäjille, että tiedostot ovat ladattavissa.",
		send_dialog_recipients_label: "Vastaanottajat:",
		send_dialog_recipients_textfield_hover_help: "Erota sähköpostiosoitteet tai käyttäjätunnukset toisistaan pilkulla. Kirjoita esimerkiksi osoite1, osoite2, käyttäjä1, käyttäjä2.",
		send_dialog_missing_recipients_message: "Määritä vähintään yksi sähköpostiosoite tai käyttäjätunnus.",
		send_dialog_title_label: "Otsikko:",
		send_dialog_note_label: "Lisää viesti.",
		send_dialog_earPassphrase_label: "Salauksen salasana:",
		send_dialog_earPassphrase_textfield_hover_help: "Salaa tiedostot palvelimessa antamalla salasana. Jatkossa vastaanottajien on annettava salasana, jotta suojattujen tiedostojen salaus puretaan latauksen yhteydessä.",
		send_dialog_notify_title: "Ilmoitus: ${0}",
		send_dialog_notifyOnUpload_label: "Ilmoita minulle, kun tiedosto on siirretty palvelimeen",
		send_dialog_notifyOnDownload_label: "Ilmoita minulle, kun tiedosto on ladattu",
		send_dialog_notifyOnUploadDownload: "Ilmoita minulle, kun tiedosto on siirretty palvelimeen ja ladattu",
		send_dialog_send_button_label: "Lähetä",
		send_dialog_started: "Paketin ${0} lähetys on meneillään.",
		status_started: "Paketin tila: ${0} - käsittelyssä (${1}%)",
		status_stopped: "Paketin tila: ${0} - pysäytetty",
		status_failed: "Paketin tila: ${0} - epäonnistunut",
		status_completed: "Paketin tila: ${0} - valmis",

		// error
		send_dialog_too_many_items_error: "Objektien lähetys ei onnistu.",
		send_dialog_too_many_items_error_explanation: "Voit lähettää enintään ${0} objektia kerrallaan. Lähetettävien objektien määrä on nyt ${1}.",
		send_dialog_too_many_items_error_userResponse: "Valitse vähemmän objekteja ja yritä lähettää objektit uudelleen. Voit myös pyytää järjestelmän pääkäyttäjää lisäämään yhdellä kertaa lähettävien objektien enimmäismäärää.",
		send_dialog_too_many_items_error_0: "objektien_enimmäismäärä",
		send_dialog_too_many_items_error_1: "objektien_määrä",
		send_dialog_too_many_items_error_number: 5050,
});

