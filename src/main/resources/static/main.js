var messageApi = Vue.resource('/blogapi/')

Vue.component('message-row',{
    props: ['message'],
    computed: {
      pageUrl(){
          return 'blog/'+ this.message.id;
      }
    },
    template: '<div class="alert alert-info mt-2">' +
        '<h3>{{message.name}}</h3>' +
        '<a class="btn btn-warning" v-bind:href="pageUrl">Открыть статью</a>' +
        '</div>'
})
Vue.component('messages-list',
    {
        props:['messages'],
        template:
        '<div>'+
            '<message-row v-for="message in messages" :key="message.id" :message="message"/>'+
        '</div>',
        created: function () {
            messageApi.get().then(result => result.json().then(data => data.forEach(message => this.messages.push(message))))
        }
    }
)

var app = new Vue({
    el: '#app',
    template: '<messages-list :messages="messages"/>',
    data: {
        messages: []
    }
})
