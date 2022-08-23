Vue.createApp({
  data() {
    return {
      charging: true,
      hidden: "",
      header: null,
      navResponsive: null,
      studentName: "Juan",
      studentCourse: "Bass",
      studentImg: "./assets/juan.png",
      studentReview: "Honestly I had an excellent learning in such a short time, the teachers are super friendly and great connoisseurs of music and rock. Thank you!",
      firstName: "",
      lastName: "",
      isLogin: false,
      courses: "",
      students: [],
      teachers: "",
      studentsMax4: [],
      
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
      }).catch(err => null),

      axios.get("/api/courses")
        .then(api => {
          this.courses = api.data

          this.coursesInStorage = JSON.parse(localStorage.getItem("cartCourse"))
          if(this.coursesInStorage != null){
               this.coursescart = this.coursesInStorage
          }
        }),

      axios.get("/api/clients")
        .then(api => {
          this.students = api.data
          for (i = 0; i <= 3; i++) {
            this.studentsMax4.push(this.students[i])
          }
        })

    axios.get("/api/teachers")
      .then(api => {
        this.teachers = api.data
      })
    setTimeout(() => { this.charging = false }, 1500)
  },

  mounted() {
    this.$nextTick(function () {
      this.header = document.querySelector(".nav");
      this.hidden = document.querySelectorAll('.hidden');
      this.navResponsive = document.querySelectorAll('.navResponsive');
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


    filterStudent(studentCur) {
      let currentStudent = this.studentsMax4.filter(student => student.firstName === studentCur)
      let arrayRewiews = ["ds", 'Honestly I had an excellent learning in such a short time, the teachers are super friendly and great connoisseurs of music and rock. Thank you!',
        'Since ays wanted to pI was a child I alwlay the guitar, thanks to the guitar course that has become a reality. I am very happy with the quality of information and practical exercises that I have been given at the academy.',
        'I started taking classes 2 weeks ago. For the moment very happy. They are very didactic and with excellent predisposition.',
        'Thanks to the piano course of this academy, I can fulfill my dream of playing professionally in a rock band, excellent quality of teachers']
      this.studentName = currentStudent[0].firstName
      this.studentCourse = "Guitar"
      this.studentImg = `./assets/${studentCur}.png`
      this.studentReview = arrayRewiews[currentStudent[0].id]
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
      goToTeacherCourses(id){
        window.location = `teacher-courses.html?id=${id}`
      },
    },
    

  computed: {
    headershow() {
      if (this.header != null) {
        window.addEventListener("scroll", () => {
          let header = this.header
          let scrolltop = document.documentElement.scrollTop;
          let top = header.offsetTop
          if (top + 100 <= scrolltop) {
            header.style.background = "#000000ff"
          }
          else {
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


  },
}).mount("#app")
