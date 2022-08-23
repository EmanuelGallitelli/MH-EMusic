Vue.createApp({
	data() {
	return {
         charging: true,
         hidden: "",
        isLogin: false,
        firstName : "",
        lastName : "",

        merchscart:[],
        merchId:[],
        merchsInStorage:[],

        coursescart:[],
        courseId:[],
        coursesInStorage:[],

        totalAmount:0,
        cardNumber:"",
        name:"",
        cvv:"",
        descripcion:"",
        date:"",

        arrayObjectCourse: [],
        arrayJSONmerch: [],

        ticketMerchinSorage:"",
        merchFinal:"",
        arrayCourseId:[]

	}
	},

	async created() {

       await axios.get(`/api/merch`)
        .then(datos => {
   
             this.merchsInStorage = JSON.parse(localStorage.getItem("cartMerch"))
             if(this.merchsInStorage != null){
                  this.merchscart = this.merchsInStorage
             }
   
        })
   
       await axios.get(`/api/courses`)
        .then(datos => {
   
             this.coursesInStorage = JSON.parse(localStorage.getItem("cartCourse"))
             if(this.coursesInStorage != null){
                  this.coursescart = this.coursesInStorage
             }
        })
        await axios
      .get("/api/client/current").then(api => {
        this.firstName = api.data.firstName
        this.lastName = api.data.lastName
        this.isLogin = true;
      }).catch(err => null)
 
      this.totalAmount = this.obtenerPrecioTotal()
      this.ticketStorage()

      this.ticketMerchinSorage = JSON.parse(localStorage.getItem("ticketMerch"))

      this.merchFinal = JSON.stringify(this.ticketMerchinSorage)
      setTimeout(() => { this.charging = false }, 1000)
	},
	

	methods: {

        ticketStorage(){
            this.merchscart.forEach(merch => {
                let obj = {
                    "id": merch.id,
                    "quantity": merch.unidadesAComprar
                }
                this.arrayJSONmerch.push(obj)
                localStorage.setItem("ticketMerch",JSON.stringify(this.arrayJSONmerch))
            })

            this.coursescart.forEach(course => {
                this.arrayCourseId.push(course.id)
            })
        },

        async ticketCompra(){
            await axios.post(`/api/ticket_transaction?idsCourses=${this.arrayCourseId}`, this.merchFinal, {headers:{'content-type':'application/json'}})
            .then(function (response) {
                this.idTicket = response?.data
                axios.get(`/pdf/generate/${this.idTicket}`)
                .then(function(response){
                    console.log("1")
                    location.href=`/pdf/generate/${this.idTicket}`
                    localStorage.removeItem("cartMerch",this.merchsInStorage)
                    localStorage.removeItem("cartCourse",this.coursesInStorage)
                    this.merchsInStorage = []
                    this.coursesInStorage = []
                    setTimeout(() => { window.location.reload() }, 1500)
                    
                })
                .catch(function (error) {
                    console.log(error);
        }); 
               
                })
            .catch(function (error) {
            console.log(error);
            });

     
         },
         async descargarPDF(id){
        axios.get(`/pdf/generate/${id}`)
        .then(function(response){
            console.log(response);
        })
        .catch(function (error) {
            console.log(error);
        }); 
        },

       async payment(){
            await axios.post(`https://homebakingmindhub.herokuapp.com/api/clients/cards/payments?thruDate=${this.date}`,{cardNumber:this.cardNumber,cvv:this.cvv,amountPayment:this.totalAmount,description:this.descripcion,name:this.name},{headers:{'Access-Control-Allow-Origin':'*'}})
            .then(response => {
                this.ticketCompra()
                console.log(response.data);
            })
        },

        deleteCartMerchs(merch){
            this.merchsInStorage = this.merchsInStorage.filter(merch1 => merch1.id != merch.id)
            this.merchscart = this.merchsInStorage
            localStorage.setItem("cartMerch",JSON.stringify(this.merchsInStorage))
       },
      
      
       deleteCartCourse(course){
            this.coursesInStorage = this.coursesInStorage.filter(course1 => course1.id != course.id)
            this.coursescart = this.coursesInStorage
            localStorage.setItem("cartCourse",JSON.stringify(this.coursesInStorage))
       },

       calcularSubtotalMerch(merch) {
        return merch.price * merch.unidadesAComprar
      },
      calcularSubtotalCourse(course) {
        return course.price
      },
      obtenerPrecioTotal() {
        let precioTotal = 0
        this.merchscart.forEach(producto => precioTotal += this.calcularSubtotalMerch(producto))
        this.coursescart.forEach(course => precioTotal += this.calcularSubtotalCourse(course))
        return precioTotal
      },

      aumentarUnidadesAComprar(merch){
        if ((merch.stock - merch.unidadesAComprar) > 0) {
             merch.unidadesAComprar++
           }
           localStorage.removeItem("cartMerch",this.merchsInStorage)
           localStorage.setItem("cartMerch",JSON.stringify(this.merchsInStorage))
           console.log(this.ticketMerchinSorage);
   },

   disminuirUnidadesAComprar(merch){
        if (merch.unidadesAComprar > 0) {
             merch.unidadesAComprar--
           }
           localStorage.removeItem("cartMerch",this.merchsInStorage)
           localStorage.setItem("cartMerch",JSON.stringify(this.merchsInStorage))
           console.log(this.ticketMerchinSorage);
   },

   logout() {
    axios
      .post("/api/logout")
      .then((response) => window.location.replace("./index.html"));
      localStorage.removeItem("cartMerch",this.merchsInStorage)
      localStorage.removeItem("cartCourse",this.coursesInStorage)
      this.merchsInStorage = []
      this.coursesInStorage = []
  },
	},
	computed: {
		
	}
	},
).mount("#app")