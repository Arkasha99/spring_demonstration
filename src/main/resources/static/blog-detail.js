var messageApi = Vue.resource('/blogapi/{id}')

Vue.component("message-info",{
    props: ["message"],
    template: '<div class="alert alert-info mt-2"></div>' +
        '<h3>{{message.name}}</h3>' +
        '<p>{{message.announce}}</p>' +
        '<p>{{message.text}}</p>',
    created: function () {
        messageApi.get().then(result => result.json().then(data => data.forEach(message => this.message.push(message))))
    }
})

var app = new Vue({
    el: '#app',
    template: '<messages-info :message="message"/>',
    data: {
        message: []
    }
})
