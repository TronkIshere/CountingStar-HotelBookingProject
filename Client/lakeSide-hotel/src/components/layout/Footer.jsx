import React from "react"
import { Col, Container, Row } from "react-bootstrap"

const Footer = () => {
    let today = new Date();
    return (
        <div class="container">
			<footer class="py-3 my-4">
				<ul class="nav justify-content-center border-bottom pb-3 mb-3">
				<li class="nav-item"><a href="/" class="nav-link px-2 text-body-secondary">Home</a></li>
				<li class="nav-item"><a href="/" class="nav-link px-2 text-body-secondary">Features</a></li>
				<li class="nav-item"><a href="/browse-all-rooms" class="nav-link px-2 text-body-secondary">Pricing</a></li>
				<li class="nav-item"><a href="/" class="nav-link px-2 text-body-secondary">FAQs</a></li>
				<li class="nav-item"><a href="/" class="nav-link px-2 text-body-secondary">About</a></li>
				</ul>
				<p class="text-center text-body-secondary">©{today.getFullYear} CountingStar, Inc</p>
			</footer>
		</div>
    );
};


export default Footer