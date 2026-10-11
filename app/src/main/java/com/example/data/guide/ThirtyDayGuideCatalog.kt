package com.example.data.guide

data class DayGuide(
    val dayNumber: Int,
    val weekPhase: Int, // 1, 2, 3, 4
    val phaseTitle: String,
    val title: String,
    val actionItem: String,
    val questionToAsk: String,
    val redFlagWarning: String,
    val wiseQuote: String
)

object ThirtyDayGuideCatalog {
    val days: List<DayGuide> = listOf(
        // ============= MINGGU 1: ASAS & NIAT SEBENAR (HARI 1 - 7) =============
        DayGuide(
            dayNumber = 1,
            weekPhase = 1,
            phaseTitle = "Minggu 1: Niat & Sempadan (Kenali Niat Sebenar)",
            title = "Tetapkan Sempadan & Matlamat Ta'aruf",
            actionItem = "Tegaskan bahawa perkenalan ini adalah untuk mencari pasangan hidup yang halal, bukan untuk 'buang masa' atau 'dating kosong'.",
            questionToAsk = "\"Apa matlamat perkenalan awak kali ini, dan berapa lama tempoh yang awak sasarkan untuk ke fasa perkahwinan?\"",
            redFlagWarning = "Jika dia berkata 'kita enjoy dulu, tengok macam mana', dia belum ada kematangan untuk berkahwin.",
            wiseQuote = "Kenal dulu, sebelum ambil keputusan besar. Hati perlu dijaga, bukan diserah percuma."
        ),
        DayGuide(
            dayNumber = 2,
            weekPhase = 1,
            phaseTitle = "Minggu 1: Niat & Sempadan (Kenali Niat Sebenar)",
            title = "Semak Ketelusan Status Perkahwinan",
            actionItem = "Minta pengesahan statusnya secara sopan: bujang, duda rasmi (dengan surat cerai sah), atau sebaliknya.",
            questionToAsk = "\"Boleh kongsikan status perkahwinan terkini awak dan adakah semua urusan dokumentasi mahkamah telah selesai sepenuhnya?\"",
            redFlagWarning = "Beralasan surat cerai belum siap atau cuba berahsia tentang perkahwinan sedia ada.",
            wiseQuote = "Ketelusan pada hari pertama menyelamatkan air mata bertahun-tahun."
        ),
        DayGuide(
            dayNumber = 3,
            weekPhase = 1,
            phaseTitle = "Minggu 1: Niat & Sempadan (Kenali Niat Sebenar)",
            title = "Ketahui Rutin Seharian & Disiplin Diri",
            actionItem = "Perhatikan jadual hariannya: adakah dia mempunyai rutin yang berdisiplin antara solat, kerja, dan rehat?",
            questionToAsk = "\"Bagaimana rutin harian awak daripada bangun pagi sampai malam? Apa aktiviti wajib awak setiap hari?\"",
            redFlagWarning = "Lelaki yang kerap berjaga malam tanpa arah dan bangun lewat petang menunjukkan disiplin hidup yang rapuh.",
            wiseQuote = "Lelaki yang mampu mengurus diri sendiri, barulah mampu memimpin keluarga."
        ),
        DayGuide(
            dayNumber = 4,
            weekPhase = 1,
            phaseTitle = "Minggu 1: Niat & Sempadan (Kenali Niat Sebenar)",
            title = "Uji Pendengar Yang Baik (Bukan Narsistik)",
            actionItem = "Bercakap tentang topik santai dan perhatikan adakah dia mendengar dengan empati atau suka memotong dan bercakap tentang dirinya sahaja.",
            questionToAsk = "\"Apa pandangan awak tentang hobi saya ini? Awak ada minat yang serupa tak?\"",
            redFlagWarning = "Semua topik dipusingkan untuk memuji dirinya sendiri atau memperlekehkan pandangan anda.",
            wiseQuote = "Pilih pasangan yang mendengar dengan hati, bukan sekadar menunggu giliran untuk bercakap."
        ),
        DayGuide(
            dayNumber = 5,
            weekPhase = 1,
            phaseTitle = "Minggu 1: Niat & Sempadan (Kenali Niat Sebenar)",
            title = "Selidiki Pengajaran Daripada Masa Lalu",
            actionItem = "Beri ruang kepadanya untuk menceritakan apa yang dipelajarinya daripada fasa hidup yang lalu.",
            questionToAsk = "\"Apa kesilapan atau kelemahan terbesar yang awak pernah buat dulu yang awak tekad tak mahu ulang lagi?\"",
            redFlagWarning = "Menganggap dirinya mangsa 100% dan memburuk-burukkan bekas pasangannya tanpa henti.",
            wiseQuote = "Kedewasaan diukur apabila seseorang berani bertanggungjawab atas kesilapan lampau."
        ),
        DayGuide(
            dayNumber = 6,
            weekPhase = 1,
            phaseTitle = "Minggu 1: Niat & Sempadan (Kenali Niat Sebenar)",
            title = "Uji Sempadan Privasi & Rasa Hormat",
            actionItem = "Nyatakan waktu anda sibuk dengan urusan kerja/ibadah dan tidak dapat membalas mesej.",
            questionToAsk = "\"Saya ada urusan penting petang ini, saya akan hubungi awak semula malam nanti ya.\"",
            redFlagWarning = "Mesej bertubi-tubi, marah, atau berprasangka buruk apabila anda lambat membalas mesej.",
            wiseQuote = "Lelaki yang matang menghormati ruang peribadi dan masa anda."
        ),
        DayGuide(
            dayNumber = 7,
            weekPhase = 1,
            phaseTitle = "Minggu 1: Niat & Sempadan (Kenali Niat Sebenar)",
            title = "Kaji Konsistensi Hari 1 hingga 7",
            actionItem = "Buka Checklist dalam aplikasi ini dan tanda skor awal bagi aspek Sahsiah & Niat.",
            questionToAsk = "\"Bagaimana rasa minggu pertama kita berbual? Ada apa-apa yang awak rasa nak bincangkan lebih terbuka?\"",
            redFlagWarning = "Perubahan sikap mendadak daripada terlalu manis kepada dingin sebaik sahaja sempadan diletakkan.",
            wiseQuote = "Tindakan seminggu pertama adalah gambaran asas perwatakan."
        ),

        // ============= MINGGU 2: UJIAN EMOSI & KONFLIK (HARI 8 - 14) =============
        DayGuide(
            dayNumber = 8,
            weekPhase = 2,
            phaseTitle = "Minggu 2: Emosi & Konflik (Tindakan vs Kata-kata)",
            title = "Perhatikan Reaksi Terhadap Tekanan Kerja",
            actionItem = "Lihat bagaimana dia mengendalikan harinya yang sangat sibuk atau penuh tekanan di tempat kerja.",
            questionToAsk = "\"Hari awak nampak mencabar hari ini, bagaimana awak selalu kendalikan tekanan kerja?\"",
            redFlagWarning = "Melepaskan geram tekanan tempat kerja kepada anda melalui kata-kata kasar atau herdikan.",
            wiseQuote = "Karakter sebenar lelaki terserlah bukan ketika senang, tetapi sewaktu dia ditekan emosi."
        ),
        DayGuide(
            dayNumber = 9,
            weekPhase = 2,
            phaseTitle = "Minggu 2: Emosi & Konflik (Tindakan vs Kata-kata)",
            title = "Uji Perbezaan Pandangan Kecil",
            actionItem = "Nyatakan pendapat yang berbeza secara sopan dalam satu topik perbincangan harian.",
            questionToAsk = "\"Saya ada pandangan yang agak berbeza tentang perkara itu. Pada pendapat saya... awak rasa macam mana?\"",
            redFlagWarning = "Meradang, mengejek, atau memaksakan kehendaknya sehingga anda terpaksa mengalah.",
            wiseQuote = "Rumah tangga perlukan tolak ansur, bukan pertandingan siapa menang siapa kalah."
        ),
        DayGuide(
            dayNumber = 10,
            weekPhase = 2,
            phaseTitle = "Minggu 2: Emosi & Konflik (Tindakan vs Kata-kata)",
            title = "Perhatikan Layanan Terhadap Orang Lain",
            actionItem = "Perhatikan cara dia bercakap dengan pelayan kedai, pemandu lain, atau pembantu penghantar barang.",
            questionToAsk = "(Pemerhatian senyap semasa makan atau keluar bersama mahram)",
            redFlagWarning = "Biadab, meninggikan suara atau memandang rendah pekerja kelas bawahan.",
            wiseQuote = "Cara dia melayan orang yang tidak dapat memberi faedah kepadanya adalah akhlak asalnya."
        ),
        DayGuide(
            dayNumber = 11,
            weekPhase = 2,
            phaseTitle = "Minggu 2: Emosi & Konflik (Tindakan vs Kata-kata)",
            title = "Uji Ketepatan Janji Temu",
            actionItem = "Tetapkan waktu perbincangan telefon atau pertemuan. Lihat ketepatan masanya.",
            questionToAsk = "\"Kita janji pukul 8.30 malam ya.\"",
            redFlagWarning = "Kerap lewat tanpa memohon maaf atau memberitahu alasan terlebih dahulu.",
            wiseQuote = "Orang yang menghargai anda akan menghargai setiap minit masa anda."
        ),
        DayGuide(
            dayNumber = 12,
            weekPhase = 2,
            phaseTitle = "Minggu 2: Emosi & Konflik (Tindakan vs Kata-kata)",
            title = "Tanya Cara Selesai Masalah Lalu",
            actionItem = "Tanya bagaimana dia menyelesaikan perselisihan faham besar dalam keluarga atau tempat kerjanya.",
            questionToAsk = "\"Bila berlaku salah faham besar dengan orang terdekat, adakah awak jenis yang bersemuka atau pendam lama?\"",
            redFlagWarning = "Suka berdendam, membalas perbuatan secara curang, atau 'merajuk' seperti kanak-kanak.",
            wiseQuote = "Penyelesaian matang memerlukan komunikasi terbuka tanpa ego melulu."
        ),
        DayGuide(
            dayNumber = 13,
            weekPhase = 2,
            phaseTitle = "Minggu 2: Emosi & Konflik (Tindakan vs Kata-kata)",
            title = "Uji Sifat Meminta Maaf",
            actionItem = "Bila berlaku sedikit salah faham kecil, perhatikan adakah dia ada sifat rendah diri memohon maaf.",
            questionToAsk = "\"Tadi saya rasa kurang selesa bila awak cakap begitu. Awak rasa apa yang patut kita buat?\"",
            redFlagWarning = "Enggan langsung mengaku salah dan menyalahkan anda 100% (gaslighting).",
            wiseQuote = "Perkataan 'maaf' yang ikhlas adalah benteng pendamai dalam rumah tangga."
        ),
        DayGuide(
            dayNumber = 14,
            weekPhase = 2,
            phaseTitle = "Minggu 2: Emosi & Konflik (Tindakan vs Kata-kata)",
            title = "Semakan Fasa 2: Catat Bukti Tindakan",
            actionItem = "Buka tab 'Jurnal Tindakan' dalam aplikasi dan catat insiden atau konsistensi tindakannya sepanjang minggu ini.",
            questionToAsk = "\"Adakah tindakannya selari dengan kata-katanya setakat ini?\"",
            redFlagWarning = "Banyak janji ditabur tetapi tindakan langsung tidak seiring.",
            wiseQuote = "Jangan jatuh cinta pada potensi atau kata-kata; jatuh cintalah pada tindakan yang konsisten."
        ),

        // ============= MINGGU 3: KEWANGAN & TANGGUNGJAWAB (HARI 15 - 21) =============
        DayGuide(
            dayNumber = 15,
            weekPhase = 3,
            phaseTitle = "Minggu 3: Kewangan & Kerjaya (Uji Tanggungjawab)",
            title = "Bincang Hala Tuju Kerjaya & Rezeki",
            actionItem = "Bincangkan secara matang mengenai punca rezekinya dan perancangan kestabilan ekonomi keluarga.",
            questionToAsk = "\"Boleh kongsikan apa cabaran terbesar dalam kerjaya awak sekarang dan bagaimana awak rancang masa depan?\"",
            redFlagWarning = "Tiada pekerjaan tetap dan tiada inisiatif mencari punca pendapatan halal.",
            wiseQuote = "Cinta perlukan persefahaman, tetapi rumah tangga berdiri atas nafkah dan tanggungjawab."
        ),
        DayGuide(
            dayNumber = 16,
            weekPhase = 3,
            phaseTitle = "Minggu 3: Kewangan & Kerjaya (Uji Tanggungjawab)",
            title = "Sentuh Isu Kewajipan Nafkah Zahir",
            actionItem = "Bincangkan konsep nafkah asas: tempat tinggal, makan minum, pakaian dan kebajikan isteri.",
            questionToAsk = "\"Bagaimana pandangan awak tentang tanggungjawab nafkah seorang suami jika isteri pun turut bekerja?\"",
            redFlagWarning = "Mewajibkan isteri menanggung separuh perbelanjaan atau mahu menumpang aset isteri.",
            wiseQuote = "Nafkah adalah hak isteri dan amanah suami di hadapan Allah."
        ),
        DayGuide(
            dayNumber = 17,
            weekPhase = 3,
            phaseTitle = "Minggu 3: Kewangan & Kerjaya (Uji Tanggungjawab)",
            title = "Bincang Tempat Tinggal Pasca Nikah",
            actionItem = "Pastikan ada perbincangan jelas tentang lokasi tempat tinggal: menyewa sendiri atau menumpang keluarga.",
            questionToAsk = "\"Lepas kahwin nanti, di mana awak rancang kita akan tinggal bersama?\"",
            redFlagWarning = "Memaksa isteri tinggal bersama keluarga mertua yang toksik atau enggan menyediakan tempat tinggal berasingan.",
            wiseQuote = "Tempat tinggal berasingan adalah hak asas demi ketenangan dan privasi isteri."
        ),
        DayGuide(
            dayNumber = 18,
            weekPhase = 3,
            phaseTitle = "Minggu 3: Kewangan & Kerjaya (Uji Tanggungjawab)",
            title = "Semak Ketelusan Komitmen Hutang",
            actionItem = "Tanya secara berlapang dada mengenai tanggungan pinjaman yang ada agar tidak terkejut selepas nikah.",
            questionToAsk = "\"Adakah awak ada apa-apa komitmen ansuran atau pinjaman peribadi yang sedang diuruskan sekarang?\"",
            redFlagWarning = "Menyembunyikan hutang ah long, hutang kad kredit melampau, atau meminta anda buat pinjaman untuknya.",
            wiseQuote = "Kejujuran kewangan sebelum nikah adalah pencegah perceraian selepas nikah."
        ),
        DayGuide(
            dayNumber = 19,
            weekPhase = 3,
            phaseTitle = "Minggu 3: Kewangan & Kerjaya (Uji Tanggungjawab)",
            title = "Perhatikan Tabiat Berbelanja",
            actionItem = "Perhatikan adakah dia berhemah mengurus duit atau boros berbelanja untuk hobi mahal tanpa simpanan.",
            questionToAsk = "\"Apa keutamaan perbelanjaan awak setiap bulan selain daripada komitmen asas?\"",
            redFlagWarning = "Gemar berjudi dalam talian, pelaburan kripto skim cepat kaya, atau boros berhutang.",
            wiseQuote = "Rezeki sedikit tetapi berkat dan diurus cermat jauh lebih membahagiakan."
        ),
        DayGuide(
            dayNumber = 20,
            weekPhase = 3,
            phaseTitle = "Minggu 3: Kewangan & Kerjaya (Uji Tanggungjawab)",
            title = "Uji Sifat Pemurah vs Kedekut",
            actionItem = "Perhatikan sama ada dia berkira dalam perkara-perkara asas atau menghargai usaha pasangan.",
            questionToAsk = "(Pemerhatian perbelanjaan santai dan kesediaan berkongsi)",
            redFlagWarning = "Sangat berkira walaupun untuk air mineral beberapa ringgit atau membebankan anda membayar semuanya.",
            wiseQuote = "Lelaki yang pemurah pada tempatnya akan membawa rezeki yang lapang dalam keluarga."
        ),
        DayGuide(
            dayNumber = 21,
            weekPhase = 3,
            phaseTitle = "Minggu 3: Kewangan & Kerjaya (Uji Tanggungjawab)",
            title = "Kemas Kini Skor Kerjaya & Kewangan",
            actionItem = "Buka Checklist dalam aplikasi dan masukkan markah untuk seksyen Kerjaya & Kewangan.",
            questionToAsk = "\"Adakah dia lelaki yang mampu menyara dan tidak akan memeras keringat anda kelak?\"",
            redFlagWarning = "Skor kewangan merah menandakan risiko eksploitasi kewangan pasca nikah.",
            wiseQuote = "Pilih lelaki yang mencari rezeki dengan keringatnya sendiri, bukan yang mencari kesenangan di atas kudrat isteri."
        ),

        // ============= MINGGU 4: KELUARGA, ANAK & KEPUTUSAN (HARI 22 - 30) =============
        DayGuide(
            dayNumber = 22,
            weekPhase = 4,
            phaseTitle = "Minggu 4: Keluarga & Keputusan (Kenal, Nilai, Pilih)",
            title = "Bincang Isu Anak (Paling Utama Bagi Ibu Tunggal)",
            actionItem = "Bincang secara mendalam penerimaan terhadap anak anda, disiplin anak dan nafkah anak.",
            questionToAsk = "\"Bagaimana pandangan awak tentang anak-anak saya? Apa peranan yang awak selesa ambil dalam hidup mereka?\"",
            redFlagWarning = "Mencadangkan anak ditinggalkan atau menunjukkan rasa rimas terhadap kewujudan anak.",
            wiseQuote = "Lelaki yang mencintai seorang ibu wajib memuliakan dan mengasihi anak-anaknya."
        ),
        DayGuide(
            dayNumber = 23,
            weekPhase = 4,
            phaseTitle = "Minggu 4: Keluarga & Keputusan (Kenal, Nilai, Pilih)",
            title = "Uji Interaksi Bersama Anak (Suasana Santai)",
            actionItem = "Rancang pertemuan santai di tempat awam (taman rekreasi/makan) bersama anak dan mahram.",
            questionToAsk = "(Perhatikan bahasa tubuh, senyuman dan kesabarannya melayan keletah anak)",
            redFlagWarning = "Kasar, herdik anak, mengabaikan anak, atau bermuka-muka hanya bila anda melihat.",
            wiseQuote = "Hati anak-anak mempunyai firasat jujur mengenai keikhlasan seseorang."
        ),
        DayGuide(
            dayNumber = 24,
            weekPhase = 4,
            phaseTitle = "Minggu 4: Keluarga & Keputusan (Kenal, Nilai, Pilih)",
            title = "Semak Tanggungjawab Nafkah Anak Kandung (Jika Duda)",
            actionItem = "Jika calon seorang duda, pastikan dia bertanggungjawab memberi nafkah anak-anak kandungnya.",
            questionToAsk = "\"Bagaimana urusan perjumpaan dan nafkah anak kandung awak dengan bekas isteri berjalan sekarang?\"",
            redFlagWarning = "Culas membayar nafkah anak kandung atau menyekat hak bekas isteri secara zalim.",
            wiseQuote = "Lelaki yang culas nafkah anak sendiri tidak akan amanah membesarkan anak orang lain."
        ),
        DayGuide(
            dayNumber = 25,
            weekPhase = 4,
            phaseTitle = "Minggu 4: Keluarga & Keputusan (Kenal, Nilai, Pilih)",
            title = "Ketahui Penerimaan Bakal Keluarga Mertua",
            actionItem = "Pastikan keluarganya mengetahui latar belakang anda dan tiada tentangan atau penghinaan status.",
            questionToAsk = "\"Bila masa yang sesuai untuk saya kenal atau berjumpa ibu dan keluarga awak?\"",
            redFlagWarning = "Takut memberitahu keluarganya atau mahu merahsiakan pernikahan daripada pengetahuan mak ayahnya.",
            wiseQuote = "Perkahwinan menyatukan dua keluarga, restu yang berkat membuahkan ketenteraman."
        ),
        DayGuide(
            dayNumber = 26,
            weekPhase = 4,
            phaseTitle = "Minggu 4: Keluarga & Keputusan (Kenal, Nilai, Pilih)",
            title = "Uji Kesediaan Berjumpa Wali Anda",
            actionItem = "Ajak dia berjumpa bapa/wali anda secara rasmi untuk memohon izin berkenalan dan melamar.",
            questionToAsk = "\"Ayah/wali saya ingin berkenalan dengan awak. Bila hujung minggu yang awak lapang untuk datang ke rumah?\"",
            redFlagWarning = "Mengelak, menangguh berkali-kali, atau mengajak bernikah di sempadan secara sembunyi.",
            wiseQuote = "Lelaki budiman masuk melalui pintu depan dengan adab dan penghormatan kepada wali."
        ),
        DayGuide(
            dayNumber = 27,
            weekPhase = 4,
            phaseTitle = "Minggu 4: Keluarga & Keputusan (Kenal, Nilai, Pilih)",
            title = "Lakukan Solat Istikharah & Doa Ketenangan",
            actionItem = "Lakukan solat istikharah pada waktu malam. Mohon petunjuk Allah agar dilapangkan jalan atau dijauhkan jika ada mudarat.",
            questionToAsk = "(Doa Istikharah: Mohon ketetapan hati yang tenang)",
            redFlagWarning = "Abaikan perasaan was-was yang berterusan jika banyak tanda amaran yang ditemui.",
            wiseQuote = "Istikharah bukan sekadar mimpi tidur, tetapi ketenangan jiwa dan kelancaran urusan yang baik."
        ),
        DayGuide(
            dayNumber = 28,
            weekPhase = 4,
            phaseTitle = "Minggu 4: Keluarga & Keputusan (Kenal, Nilai, Pilih)",
            title = "Semak Skor Analisis Penuh Aplikasi",
            actionItem = "Buka skrin Analisis dalam aplikasi. Lihat peratusan skor Sahsiah, Kerjaya, dan Kekeluargaan.",
            questionToAsk = "\"Berapa jumlah Red Flag yang ada? Adakah ia sesuatu yang membahayakan masa depan anda dan anak-anak?\"",
            redFlagWarning = "Jika ada lebih daripada 2 Red Flag kritikal dalam Sahsiah/Kewangan, jangan sesekali berkompromi.",
            wiseQuote = "Pilih dengan akal dan iman, bukan sekadar rayuan perasaan sementara."
        ),
        DayGuide(
            dayNumber = 29,
            weekPhase = 4,
            phaseTitle = "Minggu 4: Keluarga & Keputusan (Kenal, Nilai, Pilih)",
            title = "Dapatkan Pandangan Orang Dipercayai",
            actionItem = "Kongsi ringkasan analisis dan pemerhatian dengan ibu bapa, keluarga terdekat atau kaunselor berwibawa.",
            questionToAsk = "\"Apa pandangan jujur ibu/keluarga selepas melihat perwatakan dan latar belakang dia?\"",
            redFlagWarning = "Menolak mentah-mentah nasihat wali yang melihat tanda bahaya yang tidak anda sedari.",
            wiseQuote = "Pandangan orang yang sayangkan kita sering lebih tajam melihat apa yang dibutakan oleh emosi."
        ),
        DayGuide(
            dayNumber = 30,
            weekPhase = 4,
            phaseTitle = "Minggu 4: Keluarga & Keputusan (Kenal, Nilai, Pilih)",
            title = "Keputusan Muktamad: Teruskan atau Lepaskan",
            actionItem = "Buat keputusan secara sedar dan berani. Jika dia layak (Green Flag), teruskan langkah akad nikah. Jika ada bahaya, undur diri dengan bermaruah.",
            questionToAsk = "\"Adakah saya yakin bahawa lelaki ini mampu menjadi pelindung, imam dan penenang hati saya?\"",
            redFlagWarning = "Jangan berkahwin kerana rasa kasihan, terdesak atau takut bersendirian.",
            wiseQuote = "Kenali. Nilai. Pilih dengan lebih sedar. Hidup anda dan anak-anak amat berharga."
        )
    )
}
