<!DOCTYPE html>

<html lang="en">
<head>
<link rel="stylesheet"
	href="/webjars/bootstrap/4.1.3/css/bootstrap.min.css">

<link rel="stylesheet" href="/static/css/styles.css">
<script type="script" src="/webjars/bootstrap/4.1.3/js/bootstrap.min.js"></script>
<script type="script" src="/webjars/jquery/3.3.1-1/query.min.js"></script>
<link rel="stylesheet"
	href="/webjars/font-awesome/5.2.0/css/all.min.css">
<link rel="stylesheet" href="/static/css/bootstrap-social.css">

</head>

<body>
	<section class="app-heading">
		<div class="app-content header">
			<h1>OAuth 2.0 Client Application</h1>
		</div>
	</section>
	<section class="app-content main">
		<div class="container">
			<div class="row">
				<div class="col-lg-3 col-md-6 col-sm-12">
					<a type="button" class="btn btn-social btn-github"
						href="${githuburl}"> <i class="fab fa-github"></i>Sign in with
						Github
					</a>
				</div>
			</div>
			
			<div class="row margin-top">
				<div class="col-lg-3 col-md-6 col-sm-12">
					<a type="button" class="btn btn-social btn-facebook"
						href="${facebookUrl}"> <i class="fab fa-facebook"></i>Sign in with
						Facebook
					</a>
				</div>
			</div>
			<div class="row margin-top" >
				<div class="col-lg-3 col-md-6 col-sm-12">
					<a type="button" class="btn btn-social btn-github"
						href="${myappUrl}"> <i class="fas fa-atom"></i>Sign in with
						My App
					</a>
				</div>
			</div>
		</div>
	</section>

</body>


</html>