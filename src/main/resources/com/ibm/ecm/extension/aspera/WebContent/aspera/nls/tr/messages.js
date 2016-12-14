/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

define({
		// NLS_CHARSET=UTF-8

		// configuration
		configuration_pane_aspera_url: "IBM Aspera sunucusu URL adresi",
		configuration_pane_aspera_url_hover: "IBM Aspera sunucusunun URL adresini girin. Örneğin, https://anasistem_adı:kapı_numarası/aspera/faspex",
		configuration_pane_aspera_url_prompt: "HTTPS protokolü kullanmanız önerilir.",
		configuration_pane_max_docs_to_send: "Gönderilecek öğe sayısı üst sınırı",
		configuration_pane_max_docs_to_send_hover: "Kullanıcıların bir kerede en çok kaç öğe gönderebileceğini belirtin.",
		configuration_pane_max_procs_to_send: "Koşutzamanlı istek sayısı üst sınırı",
		configuration_pane_max_procs_to_send_hover: "Aynı anda çalışabilecek isteklerin üst sınırını belirtin.",
		configuration_pane_target_transfer_rate: "Hedef aktarma hızı (Mb/sn)",
		configuration_pane_target_transfer_rate_hover: "Saniye başına megabit olarak hedef aktarma hızını belirtin. Hızı lisans sınırlar.",
		configuration_pane_speed_info: "Yürürlükteki giriş düzeyi lisansınız 20 Mb/sn için. <a target='_blank' href='https://ibm.biz/BdjYHq'>Aspera Evaluation Request</a> sayfasında istekte bulunarak, Aspera Faspex için daha hızlı bir değerlendirme lisansına (10 Gb/sn'ye kadar) yükselebilirsiniz.",

		// runtime
		send_dialog_sender_title: "Gönderen: ${0}",
		send_dialog_not_set: "Ayarlanmadı",
		send_dialog_send_one: "'${0}' dosyasını gönder.",
		send_dialog_send_more: "${0} dosya gönder.",
		send_dialog_sender: "Kullanıcı adı:",
		send_dialog_password: "Parola:",
		send_dialog_missing_sender_message: "IBM Aspera sunucusunda oturum açmak için kullanıcı adı girmeniz gerekir.",
		send_dialog_missing_password_message: "IBM Aspera sunucusunda oturum açmak için parola girmeniz gerekir.",
		send_dialog_title: "IBM Aspera ile gönder",
		send_dialog_missing_title_message: "Bir başlık girmelisiniz.",
		send_dialog_info: "Dosyaları IBM Aspera sunucusuyla gönderin ve dosyaların karşıdan yüklenmeye hazır olduğunu kullanıcılara bildirin.",
		send_dialog_recipients_label: "Alıcılar:",
		send_dialog_recipients_textfield_hover_help: "Eposta adreslerini ve/ya da kullanıcı adlarını ayırmak için virgül kullanın. Örneğin, 'Adres1, Adres2, klnc1, klnc2'.",
		send_dialog_missing_recipients_message: "En az bir eposta adresi ya da kullanıcı adı belirtmelisiniz.",
		send_dialog_title_label: "Başlık:",
		send_dialog_note_label: "İleti ekle.",
		send_dialog_earPassphrase_label: "Şifreleme parolası:",
		send_dialog_earPassphrase_textfield_hover_help: "Sunucuda dosyaların şifrelenmesi için bir parola girin. Daha sonra, alıcıların karşıdan yüklenen korunmuş dosyaların şifresini çözmek için gereken parolayı girmeleri istenecektir.",
		send_dialog_notify_title: "Bildirim: ${0}",
		send_dialog_notifyOnUpload_label: "Dosya karşıya yüklenince bana haber ver",
		send_dialog_notifyOnDownload_label: "Dosya karşıdan yüklenince bana haber ver",
		send_dialog_notifyOnUploadDownload: "Dosya karşıya yüklenince ve karşıdan yüklenince bana haber ver",
		send_dialog_send_button_label: "Gönder",
		send_dialog_started: "${0} paketi gönderiliyor.",
		status_started: "Paket durumu: ${0} - Devam ediyor (${1}%)",
		status_stopped: "Paket durumu: ${0} - Durdu",
		status_failed: "Paket durumu: ${0} - Başarısız oldu",
		status_completed: "Paket durumu: ${0} - Tamamlandı",

		// error
		send_dialog_too_many_items_error: "Öğeler gönderilemiyor.",
		send_dialog_too_many_items_error_explanation: "Bir kerede en çok ${0} öğe gönderebilirsiniz. ${1} öğe göndermeye çalışıyorsunuz.",
		send_dialog_too_many_items_error_userResponse: "Daha az öğe seçin ve öğeleri göndermeyi yeniden deneyin. Ayrıca, bir kerede gönderebileceğiniz öğe sayısı üst sınırını artırması için sistem yöneticinize başvurabilirsiniz.",
		send_dialog_too_many_items_error_0: "öğe_sayısı_üst_sınırı",
		send_dialog_too_many_items_error_1: "öğelerin_sayısı",
		send_dialog_too_many_items_error_number: 5050,
});

