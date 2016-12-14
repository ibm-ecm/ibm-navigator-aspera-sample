/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

define({
		// NLS_CHARSET=UTF-8

		// configuration
		configuration_pane_aspera_url: "URL ของเซิร์ฟเวอร์ IBM Aspera",
		configuration_pane_aspera_url_hover: "ป้อน URL ของเซิร์ฟเวอร์ IBM Aspera ตัวอย่างเช่น: https://host_name:port_number/aspera/faspex",
		configuration_pane_aspera_url_prompt: "ขอแนะนำให้คุณใช้โปรโตคอล HTTPS",
		configuration_pane_max_docs_to_send: "จำนวนสูงสุดของไอเท็มที่ต้องการส่ง",
		configuration_pane_max_docs_to_send_hover: "ระบุจำนวนสูงสุดของไอเท็มที่ผู้ใช้สามารถส่งได้ในแต่ละครั้ง",
		configuration_pane_max_procs_to_send: "จำนวนสูงสุดของคำร้องขอแบบพร้อมกัน",
		configuration_pane_max_procs_to_send_hover: "ระบุจำนวนสูงสุดของคำร้องขอที่สามารถรันได้ในเวลาเดียวกัน",
		configuration_pane_target_transfer_rate: "อัตราการโอนย้ายเป้าหมาย (ในหน่วยเมกะบิต)",
		configuration_pane_target_transfer_rate_hover: "ระบุอัตราการโอนย้ายเป้าหมายในหน่วยเมกะบิตต่อวินาที อัตราถูกจำกัดไว้โดยไลเซนส์",
		configuration_pane_speed_info: "ไลเซนส์ระดับรายการของคุณมีอยู่ประมาณ 20 เมกะบิต อัพเกรดไปเป็นไลเซนส์การประเมินค่าที่เร็วกว่า (สูงสุด 10 กิกะไบต์) สำหรับ Aspera Faspex โดยร้องขอบนเพจ <a target='_blank' href='https://ibm.biz/BdjYHq'>Aspera Evaluation Request</a>",

		// runtime
		send_dialog_sender_title: "ผู้ส่ง: ${0}",
		send_dialog_not_set: "ไม่ได้ตั้งค่า",
		send_dialog_send_one: "ส่ง '${0}'",
		send_dialog_send_more: "ส่ง ${0} ไฟล์",
		send_dialog_sender: "ชื่อผู้ใช้:",
		send_dialog_password: "รหัสผ่าน:",
		send_dialog_missing_sender_message: "คุณต้องป้อนชื่อผู้ใช้เพื่อล็อกอินเข้าสู่เซิร์ฟเวอร์ IBM Aspera",
		send_dialog_missing_password_message: "คุณต้องป้อนรหัสผ่านเพื่อเข้าสู่เซิร์ฟเวอร์ IBM Aspera",
		send_dialog_title: "ส่งผ่าน IBM Aspera",
		send_dialog_missing_title_message: "คุณต้องป้อนหัวเรื่อง",
		send_dialog_info: "ส่งไฟล์ผ่านเซิร์ฟเวอร์ IBM Aspera และแจ้งเตือนผู้ใช้ว่า ไฟล์พร้อมสำหรับดาวน์โหลดแล้ว",
		send_dialog_recipients_label: "ผู้รับ:",
		send_dialog_recipients_textfield_hover_help: "ใช้เครื่องหมายคอมมาเพื่อคั่นอีเมลแอดเดรส และ/หรือชื่อผู้ใช้ ตัวอย่างเช่น ป้อน 'address1, address2, username1, username2'",
		send_dialog_missing_recipients_message: "คุณต้องระบุอย่างน้อยหนึ่งอีเมลแอดเดรสหรือชื่อผู้ใช้",
		send_dialog_title_label: "หัวเรื่อง:",
		send_dialog_note_label: "เพิ่มข้อความ",
		send_dialog_earPassphrase_label: "รหัสผ่านการเข้ารหัส:",
		send_dialog_earPassphrase_textfield_hover_help: "ป้อนรหัสผ่านเพื่อเข้ารหัสไฟล์บนเซิร์ฟเวอร์ ลำดับถัดมา ผู้รับอาจต้องป้อนรหัสผ่านสำหรับการถอดรหัสไฟล์ที่ได้รับการปกป้องไว้ในขณะที่กำลังดาวน์โหลด",
		send_dialog_notify_title: "การแจ้งให้ทราบ: ${0}",
		send_dialog_notifyOnUpload_label: "แจ้งให้ฉันทราบเมื่ออัพโหลดไฟล์แล้ว",
		send_dialog_notifyOnDownload_label: "แจ้งให้ฉันทราบเมื่อดาวน์โหลดไฟล์แล้ว",
		send_dialog_notifyOnUploadDownload: "แจ้งให้ฉันทราบเมื่ออัพโหลดและดาวน์โหลดไฟล์แล้ว",
		send_dialog_send_button_label: "ส่ง",
		send_dialog_started: "แพ็กเกจ ${0} ถูกส่งแล้ว",
		status_started: "สถานะแพ็กเกจ: ${0} - กำลังดำเนินการ (${1}%)",
		status_stopped: "สถานะแพ็กเกจ: ${0} - หยุด",
		status_failed: "สถานะแพ็กเกจ: ${0} - ล้มเหลว",
		status_completed: "สถานะแพ็กเกจ: ${0} - เสร็จสมบูรณ์",

		// error
		send_dialog_too_many_items_error: "ไม่สามารถส่งไอเท็มได้",
		send_dialog_too_many_items_error_explanation: "คุณสามารถส่งได้ถึง ${0} ไอเท็มในเวลาเดียวกัน คุณกำลังพยายามส่ง ${1} ไอเท็ม",
		send_dialog_too_many_items_error_userResponse: "เลือกไอเท็มเพียงเล็กน้อยและลองส่งไอเท็มอีกครั้ง คุณยังสามารถติดต่อผู้ดูแลระบบของคุณเพื่อเพิ่มจำนวนสูงสุดของไอเท็มที่คุณสามารถส่งได้ในหนึ่งครั้ง",
		send_dialog_too_many_items_error_0: "maximum_number_of_items",
		send_dialog_too_many_items_error_1: "number_of_items",
		send_dialog_too_many_items_error_number: 5050,
});

