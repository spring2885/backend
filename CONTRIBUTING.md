# Contributing to Spring2885

<strong>Here's some guidelines for contributing to Spring2885</strong>

## GitHub Issue Tracker

* Spring2885 uses the [Github issue tracker](https://github.com/spring2885/backend/issues)
  as the main venue for bug reports, feature requests, enhancement requests, and pull requests.
* Please do not use the issue tracker for general questions, please drop in on
  Slack and to ask questions.  Also you should be on slack periodically anyway.

## Repositories

* The repositories are split between frontend and backend.  Typically you
  you should be able to develop within your team's repository unless you 
  are working on putting the integration server together.

## Bug Report

We love good bug reports!  A good bug report is:
* Specific: Best case is the bug can be spotted in O(minutes).  
* Actionable: Knowing what was expected vs. what actually happened helped.
* Unique: Please search the existing issues first and don't file a duplicate.
* Environmental: Please indicate the OS and version you are using. Also
  list out the git commit hash of your frontend and backend projects.

Yes, this is a lot to ask for, but it makes it more likely it'll get quicky fixed.

## Pull Requests

We love Pull Requests! This is how you get changes into the spring2885 organization's
repositories.  This is the source of truth for the team.

Here's the best way to work with the Spring2885 git repository:

1. [Fork](https://help.github.com/articles/fork-a-repo/), then clone your fork, and configure the remotes:
    
    ```bash
    # Create a directory for your fork's clone.
    mkdir git
    cd git/
    # Clone your fork into the current directory (git).
    # Use your GitHub username instead of YOUR-USERNAME
    git clone https://github.com/YOUR-USERNAME/REPOSITORY-NAME.git
    # Change directory into the new directory (REPOSITORY-NAME) of your clone
    cd REPOSITORY-NAME
    # Add the remotes for the upstream repository (spring2885/REPOSITORY-NAME)
    git remote add upstream https://github.com/spring2885/REPOSITORY-NAME.git
    ```
    
2. If you have done step 1 a while ago, pull from the upstream repository to update your clone with the latest from spring2885/REPOSITORY-NAME.
    ```bash
    # make sure your branch is back onto the "master" branch
    git checkout master
    # pull (this is a fetch + merge) in the changes from the spring2885/REPOSITORY-NAME respository.
    git pull upstream master
    # push the changes from spring2885/REPOSITORY-NAME to your fork on github.
    git push
    ```
    
3. Create a new branch off of master for your feature, enhancement, or bug fix.

    ```bash
    git checkout -b <MY-BRANCH-NAME>
    ```    

4. Make your changes by editing the files and committing changes to your local repository.  Please use good commit messages that explain the changes and also reference github issues as necessary.

5. Merge any new changes from the spring2885/REPOSITORY-NAME respository into your development branch

    ```bash
    git pull upstream master
    ```    
    
6. Push your changes from your local machine to your fork on github.

    ```bash
    git push origin <MY-BRANCH-NAME>
    ```
    
7. Open a [Pull Request](https://help.github.com/articles/using-pull-requests/) with a clear and concise
   title and description of the changes.  Please reference any issues on GitHub as needed. 
   [pr]: https://github.com/spring2885/REPOSITORY-NAME/compare

8. At this point you're waiting on us. Admin will merge the requests or 
   add comments that need to be addressed ASAP.

[commit]: http://tbaggery.com/2008/04/19/a-note-about-git-commit-messages.html

