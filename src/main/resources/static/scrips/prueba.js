Vue.createApp({
    data() {
        return{
            arrayObjectMerch: [],
    arrayObjectCourse: [],
    arrayJSONmerch: "",
    arrayJSONcourse: "",
    email: "",
    password: "",
    idTicket: "",
    merchandises: [],
    filteredMerch:[],

        }
    },
    created(){
        axios.get(`/api/merch`)
     .then(datos => {
          this.merchandises = datos.data
          this.filteredMerch = this.merchandises

          this.merchsInStorage = JSON.parse(localStorage.getItem("cartMerch"))
          if(this.merchsInStorage != null){
               this.merchscart = this.merchsInStorage
          }
          let merchandisesType = this.filteredMerch
          merchandisesType.forEach (merch => { 
          if(!this.merchFilters.includes(merch.type)){
               this.merchFilters.push(merch)
          }  
     });
     })


        this.arrayObjectMerch = [
            {
                "id": 1,
                "quantity": 3
            },
            {
                "id": 2,
                "quantity": 4
            }
        ]



        this.arrayObjectCourse = [1] 
        this.arrayJSONmerch = JSON.stringify(this.arrayObjectMerch)
        this.email = "juanperez@gmail.com"
        this.password = "1234"

        this.loading = false;
    },
    methods:{
        async login(){
            await axios.post("/api/login", `email=${this.email}&password=${this.password}`)
            .then(response =>{
                console.log(response)
                console.log("autenticado")
            })
        },
        async ticketCompra(){
            var data = JSON.stringify(this.arrayObjectMerch);

            await axios.post(`/api/ticket_transaction?idsCourses=${this.arrayObjectCourse}`, this.arrayJSONmerch, {headers:{'content-type':'application/json'}})
            .then(function (response) {
                this.idTicket = response?.data
                axios.get(`/pdf/generate/${this.idTicket}`)
                .then(function(response){
                    console.log("1")
                    location.href=`/pdf/generate/${this.idTicket}`
                    
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
        }

    }
}).mount('#app')