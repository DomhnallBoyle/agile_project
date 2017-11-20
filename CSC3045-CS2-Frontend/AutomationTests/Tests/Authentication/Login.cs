﻿using AutomationTests.PageTemplates;
using AutomationTests.Util;
using NUnit.Framework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace AutomationTests.Tests.Authentication
{
    [TestFixture]
    public class Login : BaseTestClass
    {
        private UserDashboardPage UserDashboardPage;

        [OneTimeSetUp]
        public void OneTimeSetupLogin()
        {
            UserDashboardPage = new UserDashboardPage(MainWindow);

            Assert.IsTrue(LoginPage.IsCurrentPage());
        }

        [Test]
        public void ShouldSuccessfullyLoginAsUserWithNoProjects()
        {
            LoginPage.Login("user16@email.com", "Passw0rd16");

            var messageBox = MainWindow.MessageBox("Info");
            Assert.NotNull(messageBox);

            MessageBoxUtil.ClickOKButton(messageBox);

            Assert.IsTrue(UserDashboardPage.IsCurrentPage());

            UserDashboardPage.LogoutButton.Click();
        }

        [Test]
        public void ShouldSuccessfullyLoginAsUserWithProjects()
        {
            LoginPage.Login("user1@email.com", "Passw0rd1");

            Assert.IsTrue(UserDashboardPage.IsCurrentPage());

            UserDashboardPage.LogoutButton.Click();
        }

        [Test]
        public void ShouldFailLoginWithInvalidCredentials()
        {
            LoginPage.Login("BadLogin4462`[", "BadPassword33");

            var messageBox = MainWindow.MessageBox("Error");
            Assert.NotNull(messageBox);

            MessageBoxUtil.ClickOKButton(messageBox);

            LoginPage.EmailTextBox.Text = "";
            LoginPage.PasswordTextBox.Text = "";
        }
    }
}
