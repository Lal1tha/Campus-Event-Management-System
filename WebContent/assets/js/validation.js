document.addEventListener("submit", function (event) {

    const form = event.target;

    if (!form.matches("[data-validate]")) return;

    const email = form.querySelector('input[type="email"]');
    const phone = form.querySelector('input[name="phone"]');
    const password = form.querySelector('input[name="password"]');

    // Email validation
    if (email && !/^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$/.test(email.value)) {
        event.preventDefault();
        alert("Enter a valid email address.");
        email.focus();
        return;
    }

    // Phone validation
    if (phone && !/^[6-9][0-9]{9}$/.test(phone.value)) {
        event.preventDefault();
        alert("Enter a valid 10 digit phone number.");
        phone.focus();
        return;
    }

    // Password complexity ONLY for registration forms
    if (form.action.includes("register") &&
        password &&
        password.value.length > 0 &&
        !/^(?=.*[A-Z])(?=.*[0-9]).{8,}$/.test(password.value)) {

        event.preventDefault();
        alert("Password must be 8 characters with one uppercase letter and one number.");
        password.focus();
        return;
    }

});