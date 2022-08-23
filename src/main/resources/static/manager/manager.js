Vue.createApp({
    data() {
        return {
            //Merch Variables
            merchList: {},
            merchStock: 0,
            merchType: "",
            merchPrice: 0,
            merchWaist: "df",
            merchStockModal: 0,
            merchTypeModal: "",
            merchPriceModal: 0,
            merchWaistModal: "df",
            idMerchToEdit: 0 ,
            //Course Variables
            coursesList: {},
            courseName: "",
            courseLevel: "",
            courseNumberLessons: 0,
            coursePrice: 0,
            courseDuration: 0,
            courseTeacherId: 1,
            courseNameModal: "",
            courseLevelModal: "",
            courseNumberLessonsModal: 0,
            coursePriceModal: 0,
            courseDurationModal: 0,
            courseTeacherIdModal: 1,
            idCourseToEdit: 0,
            //Teacher Variables
            teachersList: {},
            teacherName: "",
            teacherLastName: "",
            teacherEmail: "",
            teacherPassword: "",
            teacherSubject: "",
            teacherEmailModal: "",
            teacherPasswordModal: "",
            teacherSubjectModal: "",
            idTeacherToEdit:"",
            errorMessage: "",
      }
    },

    created(){
        axios.get('/api/merch')
        .then(data => {
            this.merchList = data.data
        })
        
        axios.get('/api/courses')
        .then(data => {
            this.coursesList = data.data
        })

        axios.get('/api/teachers')
        .then(data => {
            this.teachersList = data.data
        })

    },

    methods:{
        // MERCH METHODS //
        addNewMerch(){
            axios.post('/api/create/merch' , {stock:`${this.merchStock}`, type:`${this.merchType}`, price:`${this.merchPrice}`, waist:`${this.merchWaist}`,headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(() =>
            Swal.fire({
                icon: 'success',
                title: 'Merch created successfully',
                showConfirmButton: false,
                timer: 1500    
            })
            .then(() => window.location.reload()))
            .catch(error => {
                console.log(error.response.data)
                this.errorMessage = error.response.data
                Swal.fire({
                  icon: 'error',
                  title: this.errorMessage,
                  confirmButtonColor: '#2691d9'
                })
              }
            )    
        },

        deleteMerch(idMerchToDelete){
            Swal.fire({
                title: "Confirm delete",
                text: "Are you sure you want to delete this merch?",
                icon: "question",
                showCancelButton: true,
                confirmButtonColor: '#2691d9',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Confirm'
            })
            .then((results) =>{
                if (results.isConfirmed){
                    axios.patch('/api/delete/merch' , `id=${idMerchToDelete}`, {headers:{'content-type':'application/x-www-form-urlencoded'}})
                      .then(() =>
                      Swal.fire({
                        icon: 'success',
                        title: 'Merch deleted successfully',
                        showConfirmButton: false,
                        timer: 1500
                      })
                      .then(() => window.location.reload())
                      )
                      .catch(error => {
                        console.log(error.response.data)
                        this.errorMessage = error.response.data
                        Swal.fire({
                          icon: 'error',
                          title: this.errorMessage,
                          confirmButtonColor: '#2691d9'
                        })
                      }
                    )
              } } )  
        },

        preloadDataModal(merch){
            this.merchStockModal = merch.stock
            this.merchTypeModal = merch.type
            this.merchPriceModal = merch.price
            this.merchWaistModal = merch.waist
            this.idMerchToEdit = merch.id
        },

        editMerch(){
            axios.patch('/api/edit/merch' , {id:`${this.idMerchToEdit}`,stock:`${this.merchStockModal}`, type:`${this.merchTypeModal}`, price:`${this.merchPriceModal}`, waist:`${this.merchWaistModal}`,headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(() =>
            Swal.fire({
                icon: 'success',
                title: 'Merch edited successfully',
                showConfirmButton: false,
                timer: 1500    
            })
            .then(() => window.location.reload()))
            .catch(error => {
                console.log(error.response.data)
                this.errorMessage = error.response.data
                Swal.fire({
                  icon: 'error',
                  title: this.errorMessage,
                  confirmButtonColor: '#2691d9'
                })
              }
            )    
        },

        // COURSE METHODS //
        addNewCourse(){
            axios.post('/api/courses' , {name:`${this.courseName}`, level:`${this.courseLevel}`, lessons:`${this.courseNumberLessons}`, price:`${this.coursePrice}`, duration:`${this.courseDuration}`, idTeacher:`${this.courseTeacherId}`,headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(() =>
            Swal.fire({
                icon: 'success',
                title: 'Course created successfully',
                showConfirmButton: false,
                timer: 1500    
            })
            .then(() => window.location.reload()))
            .catch(error => {
                console.log(error.response.data)
                this.errorMessage = error.response.data
                Swal.fire({
                  icon: 'error',
                  title: this.errorMessage,
                  confirmButtonColor: '#2691d9'
                })
              }
            )      
        },

        deleteCourse(idCourseToDelete){
            Swal.fire({
                title: "Confirm delete",
                text: "Are you sure you want to delete this course?",
                icon: "question",
                showCancelButton: true,
                confirmButtonColor: '#2691d9',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Confirm'
            })
            .then((results) =>{
                if (results.isConfirmed){
                    axios.patch('/api/delete/courses' , `idCourse=${idCourseToDelete}`, {headers:{'content-type':'application/x-www-form-urlencoded'}})
                      .then(() =>
                      Swal.fire({
                        icon: 'success',
                        title: 'Course deleted successfully',
                        showConfirmButton: false,
                        timer: 1500
                      })
                      .then(() => window.location.reload())
                      )
                      .catch(error => {
                        console.log(error.response.data)
                        this.errorMessage = error.response.data
                        Swal.fire({
                          icon: 'error',
                          title: this.errorMessage,
                          confirmButtonColor: '#2691d9'
                        })
                      }
                    )
            }}  )      
        },

        preloadDataModalCourse(course){
            this.idCourseToEdit = course.id
            this.courseNameModal = course.name
            this.courseLevelModal = course.level
            this.courseNumberLessonsModal = course.lessons
            this.coursePriceModal = course.price
            this.courseDurationModal = course.duration
            this.courseTeacherIdModal = course.teacher.id
        },

        editCourse(){
            axios.patch('/api/edit/courses' , {id:`${this.idCourseToEdit}`,name:`${this.courseNameModal}`, level:`${this.courseLevelModal}`, lessons:`${this.courseNumberLessonsModal}`, price:`${this.coursePriceModal}`, duration:`${this.courseDurationModal}`, teacherId:`${this.courseTeacherIdModal}`,headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(() =>
            Swal.fire({
                icon: 'success',
                title: 'Course edited successfully',
                showConfirmButton: false,
                timer: 1500    
            })
            .then(() => window.location.reload()))
            .catch(error => {
                console.log(error.response.data)
                this.errorMessage = error.response.data
                Swal.fire({
                  icon: 'error',
                  title: this.errorMessage,
                  confirmButtonColor: '#2691d9'
                })
              }
            )     
        },

        // TEACHER METHODS //
        addNewTeacher(){
            axios.post('/api/teachers' , `firstName=${this.teacherName}&lastName=${this.teacherLastName}&email=${this.teacherEmail}&password=${this.teacherPassword}&subject=${this.teacherSubject}` , {headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(() =>
            Swal.fire({
                icon: 'success',
                title: 'Teacher successfully added.',
                showConfirmButton: false,
                timer: 1500    
            })
            .then(() => window.location.reload()))
            .catch(error => {
                console.log(error.response.data)
                this.errorMessage = error.response.data
                Swal.fire({
                  icon: 'error',
                  title: this.errorMessage,
                  confirmButtonColor: '#2691d9'
                })
              }
            )       
        },

        deleteTeacher(idTeacherToDelete){
            Swal.fire({
                title: "Confirm delete",
                text: "Are you sure you want to delete this teacher?",
                icon: "question",
                showCancelButton: true,
                confirmButtonColor: '#2691d9',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Confirm'
            })
            .then((results) =>{
                if (results.isConfirmed){
                    axios.patch('/api/delete/teacher' , `id=${idTeacherToDelete}`, {headers:{'content-type':'application/x-www-form-urlencoded'}})
                      .then(() =>
                      Swal.fire({
                        icon: 'success',
                        title: 'Teacher deleted successfully',
                        showConfirmButton: false,
                        timer: 1500
                      })
                      .then(() => window.location.reload())
                      )
                      .catch(error => {
                        console.log(error.response.data)
                        this.errorMessage = error.response.data
                        Swal.fire({
                          icon: 'error',
                          title: this.errorMessage,
                          confirmButtonColor: '#2691d9'
                        })
                      }
                    )
            }}  )    
        },

        preloadDataModalTeacher(teacher){
            this.idTeacherToEdit = teacher.id
            this.teacherEmailModal = teacher.email
            this.teacherSubjectModal = teacher.subject
        },

        editTeacher(){
            console.log(this.teacherEmail,this.teacherPassword)
            axios.patch('/api/edit/teacher' , `id=${this.idTeacherToEdit}&email=${this.teacherEmailModal}&password=${this.teacherPasswordModal}&subject=${this.teacherSubjectModal}`, {headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(() =>
            Swal.fire({
                icon: 'success',
                title: 'Teacher edited successfully',
                showConfirmButton: false,
                timer: 1500    
            })
            .then(() => window.location.reload()))
            .catch(error => {
                console.log(error.response.data)
                this.errorMessage = error.response.data
                Swal.fire({
                  icon: 'error',
                  title: this.errorMessage,
                  confirmButtonColor: '#2691d9'
                })
              }
            )
        }
    },

  }).mount('#app')