Vue.createApp({
     data() {
          return {
               charging: true,
               hidden: "",
               header : null,
               header : null,
               courses: "",
               merchandises: [],

               filteredMerch:[],
               firstName : "",
               lastName : "",
               isLogin: false,

               merchscart:[],
               merchId:[],
               merchsInStorage:[],
               merchFilters : [],

               coursescart:[],
               courseId:[],
               coursesInStorage:[],

          }
     },

     created() {
     
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

     axios.get(`/api/courses`)
     .then(datos => {
          this.courses = datos.data

          this.coursesInStorage = JSON.parse(localStorage.getItem("cartCourse"))
          if(this.coursesInStorage != null){
               this.coursescart = this.coursesInStorage
          }
     })
     axios
     .get("/api/client/current").then(api => {
       this.firstName = api.data.firstName
       this.lastName = api.data.lastName
       this.isLogin = true;
     }).catch(err => null)

     setTimeout(() => { this.charging = false }, 1500)

     },
     
     mounted(){
     this.$nextTick(function () {
     this.header = document.querySelector(".nav");
     })

     },
     methods: {

          cartMerchs(merch){
               this.merchId = this.merchscart.map(merch1 => merch1.id)
               if(!this.merchId.includes(merch.id) && merch.stock > 0){
                    merch.stock -= 1
                    merch.unidadesAComprar = 1
                    this.merchscart.push(merch)
                    localStorage.setItem("cartMerch",JSON.stringify(this.merchscart))
               }
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

          aumentarUnidadesAComprar(merch){
               if ((merch.stock - merch.unidadesAComprar) > -1) {
                    merch.unidadesAComprar++
                  }
                  localStorage.removeItem("cartMerch",this.merchsInStorage)
           localStorage.setItem("cartMerch",JSON.stringify(this.merchsInStorage))
          },

          disminuirUnidadesAComprar(merch){
               if (merch.unidadesAComprar > 0) {
                    merch.unidadesAComprar--
                  }
                  localStorage.removeItem("cartMerch",this.merchsInStorage)
           localStorage.setItem("cartMerch",JSON.stringify(this.merchsInStorage))
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

          typeFilter(){
             
              
          },
     
          subscribeEmail(){
               const Toast = Swal.mixin({
                    toast: true,
                    position: 'top-end',
                    showConfirmButton: false,
                    timer: 3000,
                    timerProgressBar: true,
                    didOpen: (toast) => {
                    toast.addEventListener('mouseenter', Swal.stopTimer)
                    toast.addEventListener('mouseleave', Swal.resumeTimer)
                    }
               })
               
               Toast.fire({
                       icon: 'success',
                    title: 'Successfully subscribed!'
               })
               },

          filterBy($event){
               console.log($event.target.getAttribute("data-merch-type"));
               let type = $event.target.getAttribute("data-merch-type");
               let allMerch = this.merchandises
               let merchByType = allMerch.filter(product => product.type == type )
               this.filteredMerch = merchByType
               console.log(merchByType);
          },
          sortByCheapest(){
               this.filteredMerch.sort((a,b)=> a.price - b.price)
          },
          sortByMostExpensives(){
               this.filteredMerch.sort((a,b)=> b.price - a.price)
          },
          getAll(){
               this.filteredMerch = this.merchandises
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
     headershow(){
     if( this.header != null){
          window.addEventListener("scroll", () => {
          let header = this.header
          let scrolltop = document.documentElement.scrollTop;
          let top = header.offsetTop
          if(top + 100 <= scrolltop){
               header.style.background = "#000000ff"
          }
          else{
               header.style.background = "rgba(0, 0, 0, 0.0001)"
          }
          });      
     }
     }
     },
}).mount("#app")