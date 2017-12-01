﻿using AutomationTests.PageTemplates;
using AutomationTests.Util;
using NUnit.Framework;
using System.Drawing;
using TestStack.White.UIItems;
using TestStack.White.UIItems.ListBoxItems;
using TestStack.White.UIItems.WPFUIItems;

namespace AutomationTests.Tests.UserDashboard
{
    class UserDashboard : BaseTestClass
    {
        private UserDashboardPage _userDashboardPage;

        [OneTimeSetUp]
        public void OneTimeSetup()
        {
            _userDashboardPage = new UserDashboardPage(MainWindow);
        }

        [Test]
        public void ShouldSuccessfullyDisplayProjectName()
        {
            _userDashboardPage = new UserDashboardPage(MainWindow);
            LoginPage.Login("user2@e2e.com", "Aut0mation");
            Assert.IsTrue(_userDashboardPage.IsCurrentPage());
            var projectListItem = (WPFListItem)_userDashboardPage.ProjectListBox.Items.Find(item => "e2eProjectName1".Equals(item.Text));

        }

        [Test]
        public void ShouldSuccessfullyDisplayProjectDescription()
        {
            _userDashboardPage = new UserDashboardPage(MainWindow);
            LoginPage.Login("user2@e2e.com", "Aut0mation");
            Assert.IsTrue(_userDashboardPage.IsCurrentPage());
            var projectListItem = (WPFListItem)_userDashboardPage.ProjectListBox.Items.Find(item => "e2eProjectDescription1".Equals(item.Text));

        }

        [Test]
        public void ShouldSuccessfullyDisplayProductOwnderAsRole()
        {
            _userDashboardPage = new UserDashboardPage(MainWindow);
            LoginPage.Login("user3@e2e.com", "Aut0mation");
            Assert.IsTrue(_userDashboardPage.IsCurrentPage());
            var projectListItem = (WPFListItem)_userDashboardPage.ProjectListBox.Items.Find(item => "e2eProjectName1".Equals(item.Text));
            foreach (WPFListItem item in _userDashboardPage.ProjectListBox.Items)
            {
                Assert.False(item.Get<TestStack.White.UIItems.Image>("TeamMemberListScrumMasterIcon").Visible);
                Assert.False(item.Get<TestStack.White.UIItems.Image>("TeamMemberListDeveloperIcon").Visible);
                Assert.True(item.Get<TestStack.White.UIItems.Image>("TeamMemberListProductOwnerIcon").Visible);
            }

        }

        [Test]
        public void ShouldSuccessfullyDisplayManagerAsRole()
        {
            _userDashboardPage = new UserDashboardPage(MainWindow);
            LoginPage.Login("user2@e2e.com", "Aut0mation");
            Assert.IsTrue(_userDashboardPage.IsCurrentPage());
            var projectListItem = (WPFListItem)_userDashboardPage.ProjectListBox.Items.Find(item => "e2eProjectName1".Equals(item.Text));
            foreach (WPFListItem item in _userDashboardPage.ProjectListBox.Items)
            {
                Assert.True(item.Get<TestStack.White.UIItems.Image>("TeamMemberListManagerIcon").Visible);
                Assert.False(item.Get<TestStack.White.UIItems.Image>("TeamMemberListScrumMasterIcon").Visible);
                Assert.False(item.Get<TestStack.White.UIItems.Image>("TeamMemberListDeveloperIcon").Visible);
                Assert.False(item.Get<TestStack.White.UIItems.Image>("TeamMemberListProductOwnerIcon").Visible);
            }

        }
    }
}
