package com.example.data.criteria

enum class CriterionAspect(val id: String, val title: String, val iconName: String) {
    SAHSIAH("sahsiah", "Sahsiah & Emosi", "Psychology"),
    KERJAYA("kerjaya", "Kerjaya & Kewangan", "AccountBalanceWallet"),
    KEKELUARGAAN("kekeluargaan", "Kekeluargaan & Anak", "FamilyRestroom"),
    LANGKAH_SETERUSNYA("langkah", "Komitmen & Langkah Nikah", "FactCheck")
}

data class CriterionItem(
    val id: String,
    val aspect: CriterionAspect,
    val title: String,
    val subtitle: String,
    val bookRef: String, // e.g. "7 Perkara Wajib Semak: Niat"
    val importantQuestion: String,
    val observationTip: String,
    val greenFlagPoints: List<String>,
    val redFlagPoints: List<String>
)

object CriteriaCatalog {
    val allCriteria: List<CriterionItem> = listOf(
        // ================= ASPEK SAHSIAH =================
        CriterionItem(
            id = "sahsiah_niat",
            aspect = CriterionAspect.SAHSIAH,
            title = "Niat Sebenar Perkahwinan",
            subtitle = "Adakah dia mahu berkahwin atas dasar tanggungjawab atau sekadar mencari peneman/keuntungan peribadi?",
            bookRef = "Perkara 1: Niat",
            importantQuestion = "\"Kenapa awak nak berkahwin semula sekarang, dan apa sebenarnya yang awak cari dalam seorang isteri?\"",
            observationTip = "Perhatikan jika jawapannya tertumpu pada 'nak ada orang jaga makan pakai / anak' atau fokus kepada perkongsian hidup, sakinah dan ibadah.",
            greenFlagPoints = listOf(
                "Niat jelas untuk membina keluarga bahagia dan memikul amanah nafkah",
                "Mencari ketenangan (sakinah) dan menghargai nilai diri wanita",
                "Realistik mengenai cabaran perkahwinan fasa kedua"
            ),
            redFlagPoints = listOf(
                "Mencari wanita semata-mata untuk menguruskan kerja rumah atau menampung kewangannya",
                "Tergesa-gesa ajak bernikah dalam tempoh beberapa hari tanpa ta'aruf matang",
                "Hanya mencari pelampiasan nafsu atau lari daripada kesunyian sementara"
            )
        ),
        CriterionItem(
            id = "sahsiah_konflik",
            aspect = CriterionAspect.SAHSIAH,
            title = "Pengurusan Emosi & Konflik",
            subtitle = "Bagaimana dia bertindak balas apabila marah, ditegur, atau berbeza pandangan?",
            bookRef = "Perkara 5: Konflik",
            importantQuestion = "\"Bila awak tengah sangat marah atau rasa tertekan, apa perkara pertama yang awak selalu buat?\"",
            observationTip = "Uji dengan sedikit perbezaan pendapat atau perhatikan layanannya terhadap pelayan restoran / pemandu lain di jalan raya semasa jalan sesak.",
            greenFlagPoints = listOf(
                "Mampu mengawal nada suara dan memilih menenangkan diri sebelum bercakap",
                "Terbuka mendengar teguran tanpa defensif melampau",
                "Fokus mencari jalan penyelesaian, bukan memalukan pasangan"
            ),
            redFlagPoints = listOf(
                "Cepat menengking, mencarut, menghempas barang, atau 'silent treatment' menghukum berhari-hari",
                "Suka memutar belit kesalahan (gaslighting) sehingga anda yang rasa bersalah",
                "Sukar memaafkan dan suka mengungkit kesilapan kecil berulang kali"
            )
        ),
        CriterionItem(
            id = "sahsiah_masa_lalu",
            aspect = CriterionAspect.SAHSIAH,
            title = "Ketelusan Masa Lalu",
            subtitle = "Adakah dia jujur tentang sejarah hidup, bekas perkahwinan, dan kesilapan lalu?",
            bookRef = "Perkara 2: Masa Lalu",
            importantQuestion = "\"Apa pengajaran terbesar yang awak dapat daripada kegagalan perkahwinan atau hubungan awak sebelum ini?\"",
            observationTip = "Adakah dia menyalahkan 100% bekas pasangannya, atau dia matang mengakui kelemahan dirinya sendiri yang telah diperbaiki?",
            greenFlagPoints = listOf(
                "Tenang dan matang berkongsi fakta masa lalu tanpa menabur fitnah melulu",
                "Telah selesai (closure) secara emosi dengan bekas pasangan",
                "Mempunyai kesedaran untuk berubah menjadi insan lebih baik"
            ),
            redFlagPoints = listOf(
                "Menyembunyikan rekod penting (status perkahwinan sebenar, kes mahkamah, hutang lampau)",
                "Masih taksub atau menyimpan dendam membara terhadap bekas isteri",
                "Menutup maklumat keluarga dan enggan bercakap benar bila disoal"
            )
        ),
        CriterionItem(
            id = "sahsiah_integriti_janji",
            aspect = CriterionAspect.SAHSIAH,
            title = "Konsistensi Kata & Tindakan",
            subtitle = "Adakah perbuatannya selari dengan apa yang dijanjikan, atau sekadar manis mulut?",
            bookRef = "Sistem 30 Hari: Tindakan vs Kata-kata",
            importantQuestion = "\"Bila awak buat sesuatu janji, bagaimana awak pastikan ia dapat ditepati tepat pada waktunya?\"",
            observationTip = "Lihat perkara asas: adakah dia tepati masa temu janji? Adakah dia ingat apa yang pernah dia katakan, atau ayatnya sering berubah-ubah?",
            greenFlagPoints = listOf(
                "Menepati masa dan janji tanpa perlu diingatkan berkali-kali",
                "Jika ada halangan kecemasan, dia memaklumkan awal dengan sopan",
                "Bercakap berlandaskan realiti, bukan 'menjual mimpi indah'"
            ),
            redFlagPoints = listOf(
                "Banyak tabur janji manis melangit tetapi satu pun tidak ditunaikan",
                "Kerap memberi alasan klise dan membatalkan rancangan saat akhir sesuka hati",
                "Kata-kata manis hanya di bibir untuk memikat tetapi tindakan sebaliknya"
            )
        ),
        CriterionItem(
            id = "sahsiah_hormat_maruah",
            aspect = CriterionAspect.SAHSIAH,
            title = "Penghormatan Maruah & Batas Syarak",
            subtitle = "Menjaga batas pergaulan serta menghormati pendirian dan maruah anda sebagai wanita.",
            bookRef = "Panduan Ta'aruf Matang",
            importantQuestion = "\"Bagaimana pandangan awak tentang batasan pergaulan sepanjang tempoh kita berkenalan ini?\"",
            observationTip = "Adakah dia mendesak untuk berjumpa di tempat tersorok, atau dia menghormati privasi dan mahukan pertemuan di tempat awam/ditemani mahram?",
            greenFlagPoints = listOf(
                "Menjaga adab, pandangan dan tidak memaksa sentuhan fizikal",
                "Menghormati privasi dan waktu rehat/ibadah anda",
                "Mempunyai prinsip pegangan agama yang diamalkan dalam kehidupan seharian"
            ),
            redFlagPoints = listOf(
                "Gemar membuat gurauan lucah atau mendesak ke arah hubungan intim sebelum nikah",
                "Tidak menghormati bila anda menetapkan sempadan (boundaries)",
                "Memperlekehkan status atau maruah anda sebagai wanita/janda"
            )
        ),

        // ================= ASPEK KERJAYA & KEWANGAN =================
        CriterionItem(
            id = "kerjaya_punca_pendapatan",
            aspect = CriterionAspect.KERJAYA,
            title = "Kestabilan Punca Pendapatan",
            subtitle = "Mempunyai kerjaya atau perniagaan yang sah, stabil dan mencukupi untuk sara hidup.",
            bookRef = "Perkara 3: Kewangan",
            importantQuestion = "\"Boleh kongsikan secara ringkas bidang kerjaya awak dan bagaimana perancangan kerjaya awak untuk 3-5 tahun akan datang?\"",
            observationTip = "Kaji sama ada pekerjaannya wujud secara nyata atau dia kabur / berahsia mengenai syarikat dan skop tugasnya.",
            greenFlagPoints = listOf(
                "Mempunyai pekerjaan tetap atau perniagaan dengan aliran tunai sah",
                "Rajin bekerja dan berdisiplin bangun pagi mencari rezeki halal",
                "Jelas tentang laluan kerjaya dan tidak bergantung pada simpati orang lain"
            ),
            redFlagPoints = listOf(
                "Menganggur tanpa usaha mencari rezeki, mengharapkan bantuan wang daripada anda",
                "Terlibat dalam perniagaan meragukan, skim cepat kaya, atau perjudian",
                "Sering meminjam wang daripada rakan atau meminjam wang daripada anda pada awal kenal"
            )
        ),
        CriterionItem(
            id = "kerjaya_kefahaman_nafkah",
            aspect = CriterionAspect.KERJAYA,
            title = "Kefahaman Kewajipan Nafkah Zahir",
            subtitle = "Faham bahawa nafkah asas (tempat tinggal, makan minum, perubatan) adalah tanggungjawab suami.",
            bookRef = "Perkara 3: Kewangan & Hak Isteri",
            importantQuestion = "\"Bagaimana perancangan awak tentang nafkah rumah tangga dan kos sara hidup bersama nanti?\"",
            observationTip = "Dengar dengan teliti: adakah dia menganggap duit isteri adalah duit dia juga, atau dia menghormati hak harta persendirian isteri?",
            greenFlagPoints = listOf(
                "Mengakui nafkah keluarga adalah tanggungjawab utamanya sebagai ketua",
                "Menghargai jika isteri membantu secara sukarela tanpa paksaan",
                "Bersikap adil dan telus membincangkan kos sara hidup pasca nikah"
            ),
            redFlagPoints = listOf(
                "Menuntut perkongsian '50-50' secara keras atau mahu menumpang rumah/kereta isteri",
                "Memperlekehkan hak nafkah dengan anggapan 'isteri bekerja mesti sara diri'",
                "Berkira dalam belanja makan asas tetapi boros untuk hobi sendiri"
            )
        ),
        CriterionItem(
            id = "kerjaya_beban_hutang",
            aspect = CriterionAspect.KERJAYA,
            title = "Ketelusan Hutang & Liabiliti",
            subtitle = "Adakah dia telus mengenai komitmen bulanan dan tidak dibebani hutang terlarang?",
            bookRef = "Perkara 3: Kewangan Bersih",
            importantQuestion = "\"Adakah awak mempunyai komitmen hutang bulanan yang berat atau pinjaman peribadi yang perlu diuruskan?\"",
            observationTip = "Lelaki yang matang tidak malu berterus-terang tentang komitmen rasmi seperti rumah/kereta demi perancangan bersama.",
            greenFlagPoints = listOf(
                "Jujur mengenai status pinjaman dan komitmen kewangannya",
                "Tiada rekod disenaraihitamkan (muflis) atau hutang tidak berlesen",
                "Mempunyai perancangan melunaskan hutang secara berdisiplin"
            ),
            redFlagPoints = listOf(
                "Terjerat dengan hutang pinjaman wang tidak berlesen / ah long",
                "Menyembunyikan liabiliti besar yang bakal membebankan keluarga",
                "Meminta nama anda untuk membuat pinjaman peribadi atau menjadi penjamin"
            )
        ),
        CriterionItem(
            id = "kerjaya_tabiat_belanja",
            aspect = CriterionAspect.KERJAYA,
            title = "Tabiat Berbelanja & Simpanan",
            subtitle = "Mempunyai kebijaksanaan mengurus wang, bersederhana dan memiliki dana kecemasan.",
            bookRef = "Perkara 3: Kewangan",
            importantQuestion = "\"Bila dapat gaji atau untung bisnes, bagaimana awak agihkan simpanan kecemasan dan perbelanjaan harian?\"",
            observationTip = "Perhatikan gaya hidupnya: adakah dia gemar membeli barang berjenama demi menunjuk-nunjuk sedangkan duit makan sempit?",
            greenFlagPoints = listOf(
                "Mengutamakan keperluan asas berbanding kehendak mewah",
                "Menyimpan wang untuk masa depan dan kecemasan",
                "Tidak berlagak kaya di media sosial tetapi realiti sebaliknya"
            ),
            redFlagPoints = listOf(
                "Gaya hidup mewah di luar kemampuan (biar papa asal bergaya)",
                "Kerap kehabisan wang sebelum hujung bulan kerana tabiat boros",
                "Ketagih membeli tiket loteri, pertaruhan dalam talian, atau pelaburan scam"
            )
        ),

        // ================= ASPEK HUBUNGAN KEKELUARGAAN & ANAK =================
        CriterionItem(
            id = "keluarga_ibu_bapa",
            aspect = CriterionAspect.KEKELUARGAAN,
            title = "Layanan Terhadap Ibu Bapa & Keluarga",
            subtitle = "Menghormati ibu bapa dan keluarga kandung tanpa bersikap zalim terhadap bakal isteri.",
            bookRef = "Perkara 6: Keluarga",
            importantQuestion = "\"Bagaimana hubungan awak dengan mak ayah dan adik-beradik awak sekarang?\"",
            observationTip = "Cara seorang lelaki melayan ibunya dan adik-beradik perempuannya adalah cerminan bagaimana dia akan melayan isterinya.",
            greenFlagPoints = listOf(
                "Berbakti kepada ibu bapa dengan kasih sayang dan adab yang baik",
                "Mampu membuat keputusan berdikari tanpa kawalan toksik yang menindas",
                "Keluarganya mengenali dirinya sebagai seorang yang bertanggungjawab"
            ),
            redFlagPoints = listOf(
                "Menengking atau bersikap biadab terhadap ibu bapanya sendiri",
                "'Anak emak' tegar yang membiarkan keluarga mencampuri dan memfitnah tanpa usul periksa",
                "Memutuskan silaturrahim keluarga tanpa sebab syarie yang kukuh"
            )
        ),
        CriterionItem(
            id = "keluarga_anak_pasangan",
            aspect = CriterionAspect.KEKELUARGAAN,
            title = "Penerimaan Terhadap Anak Pasangan",
            subtitle = "Ikhlas menerima anak-anak pasangan (terutamanya bagi ibu tunggal/janda) tanpa syarat.",
            bookRef = "Perkara 4: Anak (Janda Bukan Akhir)",
            importantQuestion = "\"Bagaimana perasaan dan kesediaan awak untuk menerima anak-anak saya sebagai sebahagian daripada hidup awak?\"",
            observationTip = "Bawa bertemu anak dalam suasana santai (taman/makan). Perhatikan bahasa tubuhnya: adakah dia mesra, sabar, atau kekok dan dingin?",
            greenFlagPoints = listOf(
                "Sabar, mesra dan tidak kekok berinteraksi dengan anak",
                "Memahami bahawa keutamaan seorang ibu adalah kebajikan anak-anaknya",
                "Sedia menjadi pelindung dan teladan bapa yang penyayang"
            ),
            redFlagPoints = listOf(
                "Mencadangkan anak diserahkan kepada bekas suami/ibu bapa semata-mata nak hidup berdua",
                "Menunjukkan riak rimas, kasar, atau mendiskriminasi anak pasangan",
                "Berkira makanan, mainan, atau wang saku terhadap anak-anak pasangan"
            )
        ),
        CriterionItem(
            id = "keluarga_nafkah_anak_duda",
            aspect = CriterionAspect.KEKELUARGAAN,
            title = "Tanggungjawab Terhadap Anak Sendiri (Jika Duda)",
            subtitle = "Melunaskan nafkah anak-anak kandung terdahulu secara adil dan berterusan.",
            bookRef = "Perkara 4: Anak & Nafkah",
            importantQuestion = "\"Bagaimana urusan nafkah dan penjagaan anak-anak awak daripada perkahwinan lalu diuruskan sekarang?\"",
            observationTip = "Lelaki yang mengabaikan anak kandungnya sendiri kemungkinan besar akan mengabaikan anda dan anak anda kelak.",
            greenFlagPoints = listOf(
                "Konsisten membayar nafkah anak kandung tanpa perlu diperintah mahkamah",
                "Mengekalkan hubungan kasih sayang yang sihat dengan anak kandungnya",
                "Berkomunikasi secara profesional dengan bekas isteri demi kebajikan anak sahaja"
            ),
            redFlagPoints = listOf(
                "Lari daripada tanggungjawab nafkah anak kandung dengan pelbagai helah",
                "Bermusuh dan menggunakan anak sebagai senjata dendam terhadap bekas isteri",
                "Tidak mempedulikan kebajikan persekolahan atau kesihatan anak sendiri"
            )
        ),
        CriterionItem(
            id = "keluarga_penerimaan_mertua",
            aspect = CriterionAspect.KEKELUARGAAN,
            title = "Penerimaan Bakal Keluarga Mertua",
            subtitle = "Keluarga calon lelaki menyambut anda dengan lapang dada tanpa diskriminasi status.",
            bookRef = "Perkara 6: Keluarga",
            importantQuestion = "\"Adakah mak ayah dan keluarga awak sudah tahu tentang status saya, dan apa reaksi mereka?\"",
            observationTip = "Adakah dia berani memperkenalkan anda kepada keluarganya, atau dia menyembunyikan status anda kerana takut dibantah?",
            greenFlagPoints = listOf(
                "Jujur memberitahu ibu bapanya tentang status anda sejak awal",
                "Keluarganya menyambut baik dan menghormati keputusan anaknya",
                "Mempertahankan anda dengan sopan sekiranya ada salah faham keluarga"
            ),
            redFlagPoints = listOf(
                "Menyembunyikan status anda daripada pengetahuan ibu bapanya",
                "Keluarganya memandang rendah status janda/wanita berdikari",
                "Mengajak nikah sindiket atau nikah tanpa restu keluarga"
            )
        ),

        // ================= ASPEK LANGKAH SETERUSNYA =================
        CriterionItem(
            id = "langkah_jumpa_wali",
            aspect = CriterionAspect.LANGKAH_SETERUSNYA,
            title = "Kesediaan Berjumpa Wali & Keluarga",
            subtitle = "Berani bersemuka dengan wali secara beradab dan memohon persetujuan perkahwinan.",
            bookRef = "Perkara 7: Langkah Seterusnya",
            importantQuestion = "\"Bila awak bersedia untuk saya aturkan pertemuan rasmi dengan wali/keluarga saya?\"",
            observationTip = "Lelaki yang serius tidak akan berdolak-dalih berbulan-bulan apabila diajak berjumpa bapa/wali anda.",
            greenFlagPoints = listOf(
                "Bersungguh-sungguh dan teruja untuk berkenalan dengan wali anda",
                "Berani berhadapan dengan soalan keluarga dengan penuh adab",
                "Mengikut saluran adat dan syarak yang betul"
            ),
            redFlagPoints = listOf(
                "Memberi seribu satu alasan menolak berjumpa wali ('belum bersedia', 'nanti dulu')",
                "Hanya mahu berhubung secara rahsia di belakang keluarga",
                "Mencadangkan nikah lari di sempadan tanpa alasan syarak yang sah"
            )
        ),
        CriterionItem(
            id = "langkah_urusan_nikah",
            aspect = CriterionAspect.LANGKAH_SETERUSNYA,
            title = "Ketelusan Prosedur Pernikahan",
            subtitle = "Bersedia melengkapkan borang nikah rasmi, ujian saringan kesihatan, dan kursus perkahwinan.",
            bookRef = "Perkara 7: Langkah Seterusnya",
            importantQuestion = "\"Bagaimana persediaan awak untuk proses permohonan kebenaran nikah di Pejabat Agama dan saringan kesihatan?\"",
            observationTip = "Lihat kesediaannya melakukan ujian saringan HIV/kesihatan dan urusan surat cerai/status bujang rasmi.",
            greenFlagPoints = listOf(
                "Sedia mematuhi semua undang-undang syariah dan saringan kesihatan",
                "Mempunyai dokumen pengesahan status yang sah dan lengkap",
                "Mengutamakan keberkatan dan keselamatan masa depan rumah tangga"
            ),
            redFlagPoints = listOf(
                "Menolak ujian saringan kesihatan atau enggan menunjukkan dokumen cerai sah",
                "Mempunyai isteri sedia ada tetapi merahsiakannya (poligami haram/sorok)",
                "Menangguhkan tarikh nikah tanpa sebab kukuh selama bertahun-tahun"
            )
        )
    )
}
