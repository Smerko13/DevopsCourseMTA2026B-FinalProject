<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="he" dir="rtl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>הרשמה לקורס דבאופס</title>
    <link href="https://fonts.googleapis.com/css2?family=Heebo:wght@300;500;800&display=swap" rel="stylesheet">
    <style>
        body {
            margin: 0;
            padding: 0;
            /* Dark purple/blue radial gradient background */
            background: radial-gradient(circle at 10% 20%, #1c1335, #0d0914 70%);
            color: #ffffff;
            font-family: 'Heebo', sans-serif;
            min-height: 100vh;
            overflow-x: hidden;
        }

        /* Top Navigation Bar */
        nav {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 25px 5%;
        }

        .logo {
            font-weight: 800;
            font-size: 1.5rem;
            color: #7b61ff;
            letter-spacing: 1px;
        }
        
        .logo span {
            color: #62ded0;
        }

        .nav-links {
            display: flex;
            gap: 40px;
            list-style: none;
            margin: 0;
            padding: 0;
        }

        .nav-links a {
            color: #a09cb0;
            text-decoration: none;
            font-size: 0.85rem;
            text-transform: uppercase;
            letter-spacing: 1.5px;
            transition: color 0.3s ease;
        }

        .nav-links a.active, .nav-links a:hover {
            color: #ffffff;
            border-bottom: 2px solid #7b61ff;
            padding-bottom: 5px;
        }

        .nav-btn {
            background: rgba(255, 255, 255, 0.05);
            border: 1px solid rgba(255, 255, 255, 0.1);
            color: #ffffff;
            padding: 10px 24px;
            border-radius: 30px;
            cursor: pointer;
            font-family: 'Heebo', sans-serif;
            font-size: 0.9rem;
            transition: background 0.3s ease;
        }

        .nav-btn:hover {
            background: rgba(255, 255, 255, 0.1);
        }

        /* Main Hero Section */
        .hero {
            padding: 60px 5%;
            position: relative;
            max-width: 1200px;
            margin: 0 auto;
        }

        /* Faint background watermark matching the image's 'DEV' */
        .watermark {
            position: absolute;
            top: 15%;
            left: 5%;
            font-size: 15vw;
            font-weight: 800;
            color: rgba(255, 255, 255, 0.02);
            z-index: -1;
            pointer-events: none;
            user-select: none;
            line-height: 1;
        }

        /* Pill Badge */
        .badge {
            display: inline-flex;
            align-items: center;
            gap: 12px;
            background: rgba(123, 97, 255, 0.1);
            border: 1px solid rgba(123, 97, 255, 0.25);
            padding: 8px 20px;
            border-radius: 30px;
            font-size: 0.75rem;
            color: #b0a6da;
            margin-bottom: 35px;
            letter-spacing: 1px;
            text-transform: uppercase;
        }

        .badge .dot {
            width: 8px;
            height: 8px;
            background: #62ded0;
            border-radius: 50%;
            box-shadow: 0 0 10px #62ded0;
        }

        /* Main Typography */
        .title-white {
            font-size: 5.5rem;
            font-weight: 800;
            margin: 0;
            line-height: 1.1;
            letter-spacing: -2px;
        }

        .title-gradient {
            font-size: 5.5rem;
            font-weight: 800;
            margin: 0 0 25px 0;
            line-height: 1.1;
            letter-spacing: -2px;
            /* Gradient text effect */
            background: linear-gradient(90deg, #8a77f3, #62ded0);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
        }

        .subtitle {
            font-size: 1.8rem;
            color: #837d96;
            font-weight: 500;
            margin-bottom: 25px;
        }

        .description {
            color: #a09cb0;
            max-width: 550px;
            line-height: 1.7;
            margin-bottom: 45px;
            font-size: 1.1rem;
        }

        /* Interactive Controls Container */
        .controls-container {
            display: flex;
            flex-direction: column;
            gap: 20px;
            max-width: 450px;
        }

        /* Requirement: Input Text Box */
        .input-box {
            background: rgba(0, 0, 0, 0.2);
            border: 1px solid rgba(255, 255, 255, 0.1);
            padding: 16px 25px;
            border-radius: 30px;
            color: #ffffff;
            font-size: 1rem;
            font-family: 'Heebo', sans-serif;
            outline: none;
            transition: all 0.3s ease;
            width: 100%;
            box-sizing: border-box;
        }

        .input-box:focus {
            border-color: #62ded0;
            background: rgba(0, 0, 0, 0.4);
        }

        .action-buttons {
            display: flex;
            gap: 15px;
        }

        /* Requirement: Button */
        .btn-primary {
            background: rgba(123, 97, 255, 0.2);
            border: 1px solid rgba(123, 97, 255, 0.4);
            color: #ffffff;
            padding: 14px 30px;
            border-radius: 30px;
            font-size: 1rem;
            cursor: pointer;
            transition: all 0.3s ease;
            font-family: 'Heebo', sans-serif;
            flex: 1;
            text-align: center;
        }

        .btn-primary:hover {
            background: rgba(123, 97, 255, 0.4);
            box-shadow: 0 0 15px rgba(123, 97, 255, 0.3);
        }

        /* Requirement: Link (Styled as the secondary outline button from image) */
        .btn-secondary {
            background: transparent;
            border: 1px solid rgba(255, 255, 255, 0.2);
            color: #a09cb0;
            padding: 14px 30px;
            border-radius: 30px;
            font-size: 1rem;
            cursor: pointer;
            text-decoration: none;
            transition: all 0.3s ease;
            font-family: 'Heebo', sans-serif;
            flex: 1;
            text-align: center;
            display: inline-flex;
            justify-content: center;
            align-items: center;
        }

        .btn-secondary:hover {
            color: #ffffff;
            border-color: #ffffff;
        }
    </style>
</head>
<body>

    <nav>
        <div class="logo">MeTA<span>.</span></div>
        <ul class="nav-links">
            <li><a href="#" class="active">אודות</a></li>
            <li><a href="#">סילבוס</a></li>
            <li><a href="#">פרויקטים</a></li>
            <li><a href="#">הכשרה</a></li>
            <li><a href="#">צור קשר</a></li>
        </ul>
        <button class="nav-btn">התחברות ✦</button>
    </nav>

    <main class="hero">
        <div class="watermark">DEVOPS</div>

        <div class="badge">
            <span class="dot"></span> פתוח להרשמה לסמסטר ב'
        </div>
        
        <h1 class="title-white">הרשמה לקורס דבאופס</h1>
        <h1 class="title-gradient">בהנחיית משה מאמיה.</h1>
        
        <h2 class="subtitle">קורס דבאופס מקיף בהנחיית מרצה מן התעשייה</h2>
        
        <p class="description">
            פרויקט סיום מעשי המדמה סביבת עבודה אמיתית. בניית שרשרת CI/CD, אוטומציה, ניטור, ובדיקות עומסים מהפיתוח ועד לייצור.
        </p>

        <form class="controls-container">
            <input type="text" id="username" name="username" placeholder="הזן את שמך המלא להרשמה..." class="input-box" required>
            
            <div class="action-buttons">
                <button type="button" class="btn-primary" onclick="alert('הבקשה נשלחה לשרת בהצלחה!')">לינק להרשמה ➔</button>
                <a href="https://github.com" target="_blank" class="btn-secondary">מאגר GitHub</a>
            </div>
        </form>
    </main>

</body>
</html>