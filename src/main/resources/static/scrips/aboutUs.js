Vue.createApp({
	data() {
	return {
        isLogin: false,
        header : null ,
        firstName : "",
        lastName : "",
        charging: true,
        hidden: "",

        merchscart:[],
        merchId:[],
        merchsInStorage:[],

        coursescart:[],
        courseId:[],
        coursesInStorage:[],
	}
	},

	created() {
    axios.get(`/api/merch`)
     .then(datos => {

          this.merchsInStorage = JSON.parse(localStorage.getItem("cartMerch"))
          if(this.merchsInStorage != null){
               this.merchscart = this.merchsInStorage
          }

     })

     axios.get(`/api/courses`)
     .then(datos => {

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
        this.hidden = document.querySelectorAll('.hidden');
      })
	},
	methods: {
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
          scroll() {
            if (this.hidden != null) {
              window.addEventListener("scroll", () => {
                let hidden = this.hidden
                let scrolltop = document.documentElement.scrollTop;
                for (let i = 0; i < hidden.length; i++) {
                  let top = hidden[i].offsetTop;
                  if (top - 600 < scrolltop && scrolltop > 350) {
                    hidden[i].style.opacity = 1;
                    hidden[i].classList.add("showtop")
                  }
                }
              })
            }
          }
	}
	},
).mount("#app")