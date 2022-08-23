Vue.createApp({
     data() {
     return {
     charging: true,
     hidden: "",
     header : null,
     courses: "",
     filteredCourses: [],
     teachers:[],
     teacher:[],
     priceRange:[],
     filteredByPrice: [],
     firstName : "",
     lastName : "",
     isLogin: false,

     coursescart:[],
     courseId:[],
     coursesInStorage:[],

     merchscart:[],
     merchId:[],
     merchsInStorage:[],

     }
     },

     created() {          
     axios.get(`http://localhost:8080/api/courses`)
     .then(datos => {
          this.courses = datos.data
          this.filteredCourses = datos.data
          this.teachers = this.courses[0].teacher

          this.coursesInStorage = JSON.parse(localStorage.getItem("cartCourse"))
          if(this.coursesInStorage != null){
               this.coursescart = this.coursesInStorage
          }

     })
     axios.get(`http://localhost:8080/api/merch`)
     .then(datos => {
          this.merchandises = datos.data
          this.merchsInStorage = JSON.parse(localStorage.getItem("cartMerch"))
          if(this.merchsInStorage != null){
               this.merchscart = this.merchsInStorage
          }
     })
     axios
     .get("/api/client/current").then(api => {
       this.firstName = api.data.firstName
       this.lastName = api.data.lastName
       this.isLogin = true;
     }).catch(err => null)
     axios.get('/api/teachers')
     .then(res => this.teachers = res.data)
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
          
          cartCourses(course){
               this.courseId = this.coursescart.map(course1 => course1.id)
               if(!this.courseId.includes(course.id)){
                    this.coursescart.push(course)
                    localStorage.setItem("cartCourse",JSON.stringify(this.coursescart))
               }
          },

          deleteCartCourse(course){
               this.coursesInStorage = this.coursesInStorage.filter(course1 => course1.id != course.id)
               this.coursescart = this.coursesInStorage
               localStorage.setItem("cartCourse",JSON.stringify(this.coursesInStorage))
          },

         

          deleteCartMerchs(merch){
               this.merchsInStorage = this.merchsInStorage.filter(merch1 => merch1.id != merch.id)
               this.merchscart = this.merchsInStorage
               localStorage.setItem("cartMerch",JSON.stringify(this.merchsInStorage))
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
               let filter = [];
               let byLevel = this.filterByLevel($event);
               let byInstr = this.filterByInstrument($event);
               let byPrice = this.filterByPrice($event);



               if(byLevel.length > 0){
                    byLevel.forEach(course => !filter.includes(course) && filter.push(course))
               }
               if(byInstr.length > 0){
                    byInstr.forEach(course => !filter.includes(course) && filter.push(course))
               }
               if(byPrice.length > 0){
                    byPrice.forEach(course => !filter.includes(course) && filter.push(course))
               }
               this.filteredCourses = filter;
            
          
          },
         filter(){

         },
          filterByLevel($event){

               let attr = $event.target.getAttribute('data-filter-by')
               let filteredByLevel = [];
               filteredByLevel = this.courses.filter(course => course.level == attr )
               return filteredByLevel

          }, 
          filterByInstrument($event){
               let attr = $event.target.getAttribute('data-filter-by')
               let filteredByInstr = this.courses.filter(course => course.name == attr)
               return filteredByInstr;
          },
          filterByPrice($event){
             
               let lowerPrice = Number.parseInt($event.target.getAttribute('data-lower-price'))
               console.log(lowerPrice);
               let higherPrice = Number.parseInt($event.target.getAttribute('data-higher-price'));
               console.log(higherPrice);
               let result = this.courses.filter(course => course.price >= lowerPrice && course.price <= higherPrice)
               return result
            
          },
          filterByTeacher(teacherEmail){
               let coursesByTeacher = this.courses.filter(course => course.teacher.email == teacherEmail)
               this.filteredCourses = coursesByTeacher;
          },
          getAll(){
               this.filteredCourses = this.courses
          },

          getCoursesByTeacher(teacherEmail){
               let coursesByTeacher = this.courses.filter(course => course.teacher.email == teacherEmail)
               this.filteredCourses = coursesByTeacher;
               console.log(teacherEmail, coursesByTeacher);
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
     },

     },
}).mount("#app")